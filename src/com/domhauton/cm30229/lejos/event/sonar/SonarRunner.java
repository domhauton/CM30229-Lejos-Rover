package com.domhauton.cm30229.lejos.event.sonar;

import com.domhauton.cm30229.lejos.util.EventUtils;
import com.domhauton.cm30229.lejos.util.Proximity;
import lejos.nxt.*;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarRunner implements Runnable {
  private final static int MEASUREMENT_CNT = 7;

  private final static int DEGREE_OFFSET = 90;

  private static double FORWARD_NEAR_CAP = 24.0;
  private static double FORWARD_MID_CAP = 28.0;

  private static double LEFT_NEAR_CAP = 25.0;
  private static double LEFT_MID_CAP = 36.0;

  private static double RIGHT_NEAR_CAP = 11.0;
  private static double RIGHT_MID_CAP = 26.0;

  private final long loopTimeLength;
  private final UltrasonicSensor ultrasonicSensor;
  private final NXTRegulatedMotor swivelMotor;
  private final SonarEventCallback sonarEventCallback;
  private boolean running;
  private long nextLoopTime = 0L;
  //  private long loopCounter = 0L;
  private SonarEvent lastEvent;

  private boolean leftActive;
  private boolean rightActive;

  private SonarPosition currentSonarPosition;

  public SonarRunner(int sensorPollRate, SonarEventCallback sonarEventCallback) {
    this.sonarEventCallback = sonarEventCallback;
    this.loopTimeLength = 1000L / sensorPollRate;
    running = true;
    ultrasonicSensor = new UltrasonicSensor(SensorPort.S4);
    ultrasonicSensor.setMode(UltrasonicSensor.MODE_PING);
    swivelMotor = Motor.B;
    lastEvent = new SonarEvent(Proximity.FAR, Proximity.FAR, Proximity.FAR);
    currentSonarPosition = SonarPosition.FORWARD;
    leftActive = true;
    rightActive = true;
  }

  @Override
  public void run() {
    running = true;
    while (running) {
      nextLoopTime = EventUtils.rateLimitSleep(nextLoopTime, loopTimeLength);

      if (running) {
        scanForward();
      }
      if (leftActive && running) {
        scanLeft();
      }
      if (leftActive && rightActive && running) {
        scanForward();
      }
      if (rightActive && running) {
        scanRight();
      }
//      loopCounter++;
    }
    moveSonarTo(SonarPosition.FORWARD);
  }

  private void scanForward() {
    moveSonarTo(SonarPosition.FORWARD);
    double forwardDistance = getSonarDistance();
    Proximity forwardProximity = Proximity.getProximityIncreasing(forwardDistance, FORWARD_NEAR_CAP, FORWARD_MID_CAP);

    lastEvent = new SonarEvent(null, null, forwardProximity);
    sonarEventCallback.sendSonarEvent(lastEvent);
  }

  private void scanLeft() {
    moveSonarTo(SonarPosition.LEFT);
    double leftDistance = getSonarDistance();
    Proximity leftProximity = Proximity.getProximityIncreasing(leftDistance, LEFT_NEAR_CAP, LEFT_MID_CAP);

    lastEvent = new SonarEvent(leftProximity, null, null);
    sonarEventCallback.sendSonarEvent(lastEvent);
  }

  private void scanRight() {
    moveSonarTo(SonarPosition.RIGHT);
    double rightDistance = getSonarDistance();
    Proximity rightProximity = Proximity.getProximityIncreasing(rightDistance, RIGHT_NEAR_CAP, RIGHT_MID_CAP);

    lastEvent = new SonarEvent(null, rightProximity, null);
    sonarEventCallback.sendSonarEvent(lastEvent);
  }

  private synchronized void moveSonarTo(SonarPosition position) {
    int movementRequired = position.getOffset() - currentSonarPosition.getOffset();
    if (movementRequired != 0) {
      swivelMotor.rotate(movementRequired);
      currentSonarPosition = position;
    }
  }

  public void runCalibrationSequence() {
    //Near calibration
    moveSonarTo(SonarPosition.LEFT);

    EventUtils.debugDisplay1("Near Left Calib");
    EventUtils.debugDisplay2("Press Center");
    Button.ENTER.waitForPress();
    LEFT_NEAR_CAP = getSonarDistance();

    EventUtils.debugDisplay1("Mid Left Calib");
    Button.ENTER.waitForPress();
    LEFT_MID_CAP = getSonarDistance();

    String leftResult = "Res: " +
            (int) LEFT_NEAR_CAP + " " +
            (int) LEFT_MID_CAP + " ";

    EventUtils.debugDisplay1(leftResult);
    Button.ENTER.waitForPress();

    moveSonarTo(SonarPosition.RIGHT);

    EventUtils.debugDisplay1("Near Right Calib");
    Button.ENTER.waitForPress();
    RIGHT_NEAR_CAP = getSonarDistance();

    EventUtils.debugDisplay1("Mid Right Calib.");
    Button.ENTER.waitForPress();
    RIGHT_MID_CAP = getSonarDistance();

    String rightResult = "Res: " +
            (int) RIGHT_NEAR_CAP + " " +
            (int) RIGHT_MID_CAP + " ";

    EventUtils.debugDisplay1(rightResult);
    Button.ENTER.waitForPress();

    moveSonarTo(SonarPosition.FORWARD);

    EventUtils.debugDisplay1("Near For Calib");
    Button.ENTER.waitForPress();
    FORWARD_NEAR_CAP = getSonarDistance();

    EventUtils.debugDisplay1("Mid For Calib");
    Button.ENTER.waitForPress();
    FORWARD_MID_CAP = getSonarDistance();

    String forwardResult = "Res: " +
            (int) FORWARD_NEAR_CAP + " " +
            (int) FORWARD_MID_CAP + " ";

    EventUtils.debugDisplay1(forwardResult);
    Button.ENTER.waitForPress();
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

  public void setLeftActive(boolean leftActive) {
    this.leftActive = leftActive;
  }

  public void setRightActive(boolean rightActive) {
    this.rightActive = rightActive;
  }

  private enum SonarPosition {
    LEFT(DEGREE_OFFSET),
    FORWARD(0),
    RIGHT(-DEGREE_OFFSET);

    private final int offset;

    SonarPosition(int offset) {
      this.offset = offset;
    }

    public int getOffset() {
      return offset;
    }
  }
}
