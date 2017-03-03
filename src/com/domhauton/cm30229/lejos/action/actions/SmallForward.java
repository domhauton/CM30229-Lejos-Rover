package com.domhauton.cm30229.lejos.action.actions;

import com.domhauton.cm30229.lejos.action.movement.MovementController;

/**
 * Created by Dominic Hauton on 27/02/17.
 */
public class SmallForward implements ExecutableAction {
  @Override
  public void execute(MovementController movementController) {
    movementController.smallForward();
  }
}
