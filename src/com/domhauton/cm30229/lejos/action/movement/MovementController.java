package com.domhauton.cm30229.lejos.action.movement;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public class MovementController {
  private final static int SMALL_TURN_DISTANCE = 20;
  private final static double ARC_RADIUS = 200.0f;
  private final DifferentialPilot differentialPilot;

  public MovementController() {
    NXTRegulatedMotor motorLeft = Motor.A;
    NXTRegulatedMotor motorRight = Motor.C;
    differentialPilot = new DifferentialPilot(56, 102, motorLeft, motorRight);
  }

  public void moveForward() {
    differentialPilot.forward();
  }

  public void moveBackward() {
    differentialPilot.backward();
  }

  public void stop() {
    differentialPilot.stop();
  }

  public void halfSpin() {
    differentialPilot.rotate(180);
  }

  public void smallLeftTurn() {
    differentialPilot.rotate(-SMALL_TURN_DISTANCE);
  }

  public void smallRightTurn() {
    differentialPilot.rotate(SMALL_TURN_DISTANCE);
  }

  public void arcRight() {
    differentialPilot.arcForward(ARC_RADIUS);
  }

  public void arcLeft() {
    differentialPilot.arcForward(-ARC_RADIUS);
  }
}
