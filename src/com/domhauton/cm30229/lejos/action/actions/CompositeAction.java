package com.domhauton.cm30229.lejos.action.actions;

import com.domhauton.cm30229.lejos.action.movement.MovementController;

/**
 * Created by Dominic Hauton on 27/02/17.
 */
public class CompositeAction implements ExecutableAction {
  private final ExecutableAction[] actions;

  public CompositeAction(ExecutableAction[] actions) {
    this.actions = actions;
  }

  @Override
  public void execute(MovementController movementController) {
    for (ExecutableAction action : actions) {
      action.execute(movementController);
    }
  }
}
