package com.domhauton.cm30229.lejos.action.actions;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public enum Action {
  FORWARD(new MoveForward()),
  BACKWARD(null),
  ROTATE_LEFT(null),
  ROTATE_RIGHT(null),
  ROTATE_180(null),
  IDLE(new Idle());

  private ExecutableAction executableAction;

  Action(ExecutableAction executableAction) {
    this.executableAction = executableAction;
  }

  public ExecutableAction getExecutableAction() {
    return executableAction;
  }
}
