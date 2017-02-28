package com.domhauton.cm30229.lejos.action.actions;

import com.domhauton.cm30229.lejos.action.movement.MovementController;

/**
 * Created by Ryan Cullen on 27/02/17.
 */
class ForwardStep implements ExecutableAction {
  @Override
  public void execute(MovementController movementController) {
	  movementController.getDifferentialPilot().travel(150);
  }
}
