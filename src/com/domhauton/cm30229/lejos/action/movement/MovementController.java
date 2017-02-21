package com.domhauton.cm30229.lejos.action.movement;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public class MovementController {
  private final NXTRegulatedMotor motorLeft;
  private final NXTRegulatedMotor motorRight;

  public MovementController() {
    motorLeft = Motor.A;
    motorRight = Motor.C;
  }

  public NXTRegulatedMotor getMotorLeft() {
    return motorLeft;
  }

  public NXTRegulatedMotor getMotorRight() {
    return motorRight;
  }
}
