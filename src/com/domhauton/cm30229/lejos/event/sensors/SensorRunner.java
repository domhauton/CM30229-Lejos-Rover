package com.domhauton.cm30229.lejos.event.sensors;

import com.domhauton.cm30229.lejos.util.EventUtils;
import com.domhauton.cm30229.lejos.util.Proximity;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

/**
 * Created by dominic on 10/02/17.
 */
public class SensorRunner implements Runnable {
    private final SensorEventCallback sensorEventCallback;
    private final long loopTimeLength;
//    private long loopCounter = 0L;

    private final static int OPTICAL_SENSE_COUNT = 10;
    private static double OPTICAL_SENSE_LOW_CAP = 33.0f;
    private static double OPTICAL_SENSE_MID_CAP = 36.0f;

    private long nextLoopTime = 0L;
    private boolean running;

    private TouchSensor touchSensorFront;
    private TouchSensor touchSensorBack;
    private LightSensor lightSensor;

    public SensorRunner(int sensorPollRate, SensorEventCallback sensorEventCallback) {
        this.sensorEventCallback = sensorEventCallback;
        loopTimeLength = 1000L/sensorPollRate;
        touchSensorFront = new TouchSensor(SensorPort.S2);
        touchSensorBack = new TouchSensor(SensorPort.S1);
        lightSensor = new LightSensor(SensorPort.S3);
    }


    @Override
    public void run() {
        running = true;
        while(running) {
            nextLoopTime = EventUtils.rateLimitSleep(nextLoopTime, loopTimeLength);
            Proximity backProximity = touchSensorBack.isPressed() ? Proximity.NEAR : Proximity.FAR;
            Proximity frontProximity = getLightSensorProximity();
            frontProximity = touchSensorFront.isPressed() ? Proximity.NEAR : frontProximity;
            SensorEvent sensorEvent = new SensorEvent(frontProximity, backProximity);
            sensorEventCallback.sendSensorEvent(sensorEvent);
//            loopCounter++;
        }
    }

    public void runCalibrationSequence() {
        EventUtils.debugDisplay1("Near F Calib");
        EventUtils.debugDisplay2("Press Center");
        Button.ENTER.waitForPress();
        OPTICAL_SENSE_LOW_CAP = getTrueDistance();

        EventUtils.debugDisplay1("Mid F Calib");
        Button.ENTER.waitForPress();
        OPTICAL_SENSE_MID_CAP = getTrueDistance();

        String frontResult = "Res: " +
                OPTICAL_SENSE_LOW_CAP + " " +
                OPTICAL_SENSE_MID_CAP;

        EventUtils.debugDisplay1(frontResult);
        Button.ENTER.waitForPress();
    }

    private synchronized Proximity getLightSensorProximity() {
        double trueDistance = getTrueDistance();
        return Proximity.getProximityDecreasing(trueDistance, OPTICAL_SENSE_LOW_CAP, OPTICAL_SENSE_MID_CAP);
    }

    private double getTrueDistance() {
        double[] measurements = new double[OPTICAL_SENSE_COUNT];
        for(int i = 0; i < measurements.length; i++) {
            measurements[i] = lightSensor.readValue();
        }
        return EventUtils.getAverageDistance(measurements);
    }

    public void stop() {
        running = false;
    }
}
