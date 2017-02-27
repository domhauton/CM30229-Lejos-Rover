package com.domhauton.cm30229.lejos.action.actions;

import com.domhauton.cm30229.lejos.action.movement.MovementController;

/**
 * Created by Ryan Cullen on 27/02/17.
 */
class BackwardStep implements ExecutableAction {
  @Override
  public void execute(MovementController movementController) {
    movementController.getMotorLeft().rotate(-90);
    movementController.getMotorRight().rotate(-90);
  }
}
