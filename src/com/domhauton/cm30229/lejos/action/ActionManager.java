package com.domhauton.cm30229.lejos.action;

import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.action.movement.MovementController;

/**
 * Created by dominic on 08/02/17.
 */
public class ActionManager {
  private final MovementController movementController;

  public ActionManager() {
    movementController = new MovementController();
  }

  public void executeAction(Action action) {
    action.getExecutableAction().execute(movementController);
  }
}
