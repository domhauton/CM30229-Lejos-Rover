package com.domhauton.cm30229.lejos.event.sonar;

import com.domhauton.cm30229.lejos.util.EventUtils;
import com.domhauton.cm30229.lejos.util.Proximity;
import lejos.nxt.*;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarRunner implements Runnable {
  private final static int MEASUREMENT_CNT = 15;

  private final static int LEFT_DEGREE_OFFSET = 70;
  private final static int RIGHT_DEGREE_OFFSET = 70;

  private static double LEFT_NEAR_CAP = 27.0;
  private static double LEFT_MID_CAP = 35.0;
  private static double RIGHT_NEAR_CAP = 17.0;
  private static double RIGHT_MID_CAP = 30.0;
  private final long loopTimeLength;
  private final UltrasonicSensor ultrasonicSensor;
  private final NXTRegulatedMotor swivelMotor;
  private final SonarEventCallback sonarEventCallback;
  private boolean running;
  private long nextLoopTime = 0L;
  private long loopCounter = 0L;
  private SonarEvent lastEvent;

  public SonarRunner(int sensorPollRate, SonarEventCallback sonarEventCallback) {
    this.sonarEventCallback = sonarEventCallback;
    this.loopTimeLength = 1000L / sensorPollRate;
    running = true;
    ultrasonicSensor = new UltrasonicSensor(SensorPort.S4);
    ultrasonicSensor.setMode(UltrasonicSensor.MODE_PING);
    swivelMotor = Motor.B;
    lastEvent = new SonarEvent(Proximity.FAR, Proximity.FAR);
  }

  @Override
  public void run() {
    running = true;
    swivelMotor.rotate(LEFT_DEGREE_OFFSET);
    while (running) {
      nextLoopTime = EventUtils.rateLimitSleep(nextLoopTime, loopTimeLength);
      double leftDistance = getSonarDistance();
      Proximity leftProximity = Proximity.getProximityIncreasing(leftDistance, LEFT_NEAR_CAP, LEFT_MID_CAP);

      lastEvent = new SonarEvent(leftProximity, lastEvent.getRightProximity());
      sonarEventCallback.sendSonarEvent(lastEvent);

      swivelMotor.rotate(-(LEFT_DEGREE_OFFSET + RIGHT_DEGREE_OFFSET));
      double rightDistance = getSonarDistance();
      Proximity rightProximity = Proximity.getProximityIncreasing(rightDistance, RIGHT_NEAR_CAP, RIGHT_MID_CAP);

      lastEvent = new SonarEvent(lastEvent.getLeftProximity(), rightProximity);
      sonarEventCallback.sendSonarEvent(lastEvent);

      swivelMotor.rotate(LEFT_DEGREE_OFFSET + RIGHT_DEGREE_OFFSET);
      loopCounter++;
    }
    swivelMotor.rotate(-LEFT_DEGREE_OFFSET);
  }

  public void runCalibrationSequence() {
    //Near calibration
    swivelMotor.rotate(LEFT_DEGREE_OFFSET);

    EventUtils.debugDisplay1("Near R Calib");
    EventUtils.debugDisplay2("Press Center");
    Button.ENTER.waitForPress();
    LEFT_NEAR_CAP = getSonarDistance();

    EventUtils.debugDisplay1("Mid R Calib");
    Button.ENTER.waitForPress();
    LEFT_MID_CAP = getSonarDistance();

    String leftResult = "Res: " +
            LEFT_NEAR_CAP + " " +
            LEFT_MID_CAP + " ";

    EventUtils.debugDisplay1(leftResult);
    Button.ENTER.waitForPress();

    swivelMotor.rotate(-(LEFT_DEGREE_OFFSET + RIGHT_DEGREE_OFFSET));

    EventUtils.debugDisplay1("Near L Calib");
    Button.ENTER.waitForPress();
    RIGHT_NEAR_CAP = getSonarDistance();

    EventUtils.debugDisplay1("Mid L Calib.");
    Button.ENTER.waitForPress();
    RIGHT_MID_CAP = getSonarDistance();

    String rightResult = "Res: " +
            RIGHT_NEAR_CAP + " " +
            RIGHT_MID_CAP + " ";

    EventUtils.debugDisplay1(rightResult);
    Button.ENTER.waitForPress();

    swivelMotor.rotate(RIGHT_DEGREE_OFFSET);
  }

  private double getSonarDistance() {
    double[] measurements = new double[MEASUREMENT_CNT];
    for (int i = 0; i < measurements.length; i++) {
      ultrasonicSensor.ping();
      measurements[i] = (double) ultrasonicSensor.getDistance();
    }
    return EventUtils.getAverageDistance(measurements);
  }

  public void stop() {
    running = false;
  }
}
