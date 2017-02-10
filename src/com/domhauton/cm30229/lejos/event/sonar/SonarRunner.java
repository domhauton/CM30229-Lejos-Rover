package com.domhauton.cm30229.lejos.event.sonar;

import com.domhauton.cm30229.lejos.event.Proximity;
import lejos.nxt.*;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarRunner implements Runnable {
    private final static int MEASUREMENT_CNT = 5;

    private final static int LEFT_DEGREE_OFFSET = 10;
    private final static int RIGHT_DEGREE_OFFSET = 10;

    private final static double LEFT_NEAR_CAP = 10.0;
    private final static double LEFT_MID_CAP = 30.0;
    private final static double RIGHT_NEAR_CAP = 10.0;
    private final static double RIGHT_MID_CAP = 30.0;

    private boolean running;

    private final long loopTimeLength;
    private long nextLoopTime = 0L;

    private final UltrasonicSensor ultrasonicSensor;
    private final NXTRegulatedMotor swivelMotor;
    private final SonarEventCallback sonarEventCallback;

    public SonarRunner(int sensorPollRate, SonarEventCallback sonarEventCallback) {
        this.sonarEventCallback = sonarEventCallback;
        this.loopTimeLength = 1000L/sensorPollRate;
        running = true;
        ultrasonicSensor = new UltrasonicSensor(SensorPort.S4);
        ultrasonicSensor.setMode(UltrasonicSensor.MODE_PING);
        swivelMotor = Motor.B;
    }

    @Override
    public void run() {
        while(running) {
            rateLimitSleep();
            swivelMotor.rotate(LEFT_DEGREE_OFFSET);
            double leftDistance = getAverageDistance();
            Proximity leftProximity = Proximity.getProximity(leftDistance, LEFT_NEAR_CAP, LEFT_MID_CAP);
            swivelMotor.rotate(-(LEFT_DEGREE_OFFSET + RIGHT_DEGREE_OFFSET));
            double rightDistance = getAverageDistance();
            Proximity rightProximity = Proximity.getProximity(rightDistance, RIGHT_NEAR_CAP, RIGHT_MID_CAP);
            swivelMotor.rotate(RIGHT_DEGREE_OFFSET);
            SonarEvent sonarEvent = new SonarEvent(leftProximity, rightProximity);
            sonarEventCallback.sendSonarEvent(sonarEvent);
        }
    }

    private double getAverageDistance() {
        // Get all measurements and naive mean;
        double[] measurements = new double[MEASUREMENT_CNT];
        double total = 0;
        for(int i = 0; i < measurements.length; i++) {
            ultrasonicSensor.ping();
            double val = (double) ultrasonicSensor.getDistance();
            total += val;
            measurements[i] = val;
        }
        double mean = total/(double) MEASUREMENT_CNT;

        // Calculate standard deviation

        double variance = 0.0f;
        for(int i = 0; i < measurements.length; i++) {
            variance += Math.pow(measurements[i] - mean, 2);
        }
        double sd = Math.sqrt(variance);

        // Prune values more than 2SD out from the mean.

        double cleanMean = 0.0f;
        int usefulValues = 0;
        double upperCap = mean + (2*sd);
        double lowerCap = mean - (2*sd);

        for(int i = 0; i < measurements.length; i++) {
            if(lowerCap < i && i < upperCap) {
                usefulValues++;
                cleanMean += measurements[i];
            }
        }

        // If everything pruned just return the naive mean

        if(usefulValues != 0) {
            return cleanMean/(double) usefulValues;
        } else {
            System.out.println("Sonar value unclean!");
            return mean;
        }
    }

    private void rateLimitSleep() {
        long waitTime = nextLoopTime - System.currentTimeMillis();
        if(waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                System.err.println("Sonar Runner sleep interrupted");
            }
        }
        nextLoopTime = System.currentTimeMillis() + loopTimeLength;
    }

    public void stop() {
        running = false;
    }
}
