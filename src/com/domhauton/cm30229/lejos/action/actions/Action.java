package com.domhauton.cm30229.lejos.action.actions;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public enum Action {
  FORWARD(new MoveForward()),
  BACKWARD(new MoveBackward()),
  ROTATE_SMALL_LEFT(new RotateSmallLeft()),
  ROTATE_SMALL_RIGHT(new RotateSmallRight()),
  ROTATE_180(new Rotate180()),
  ARC_LEFT(new ArcLeft()),
  ARC_RIGHT(new ArcRight()),
  IDLE(new Idle());

  private ExecutableAction executableAction;

  Action(ExecutableAction executableAction) {
    this.executableAction = executableAction;
  }

  public ExecutableAction getExecutableAction() {
    return executableAction;
  }
}
