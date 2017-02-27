package com.domhauton.cm30229.lejos.action.movement;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public class MovementController {
  private final static int SMALL_TURN_DISTANCE = 50;
  private final static double ARC_RADIUS = 400.0f;
  private final static int SMALL_TRAVEL = 50;

  private final static double TRAVEL_SPEED = 1.0;

  private final DifferentialPilot differentialPilot;

  public MovementController() {
    NXTRegulatedMotor motorLeft = Motor.A;
    NXTRegulatedMotor motorRight = Motor.C;
    differentialPilot = new DifferentialPilot(56, 102, motorLeft, motorRight);
    differentialPilot.setTravelSpeed(TRAVEL_SPEED);
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

  public void smallBackward() {
    differentialPilot.travel(-SMALL_TRAVEL);
  }

  public void smallForward() {
    differentialPilot.travel(SMALL_TRAVEL);
  }
}
