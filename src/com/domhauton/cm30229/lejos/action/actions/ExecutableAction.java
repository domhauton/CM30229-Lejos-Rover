package com.domhauton.cm30229.lejos.action.actions;

import com.domhauton.cm30229.lejos.action.movement.MovementController;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public interface ExecutableAction {
  /**
   * Start given action.
   * @param movementController
   */
  void execute(MovementController movementController);
}
