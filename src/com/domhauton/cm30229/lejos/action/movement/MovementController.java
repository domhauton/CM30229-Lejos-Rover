package com.domhauton.cm30229.lejos.action.movement;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public class MovementController {
  private final NXTRegulatedMotor motorLeft;
  private final NXTRegulatedMotor motorRight;
  private final DifferentialPilot differentialPilot;

  public MovementController() {
    motorLeft = Motor.A;
    motorRight = Motor.C;
    differentialPilot = new DifferentialPilot(56, 109, motorLeft, motorRight);
  }

  public NXTRegulatedMotor getMotorLeft() {
    return motorLeft;
  }

  public NXTRegulatedMotor getMotorRight() {
    return motorRight;
  }

  public DifferentialPilot getDifferentialPilot() {
    return differentialPilot;
  }
}
