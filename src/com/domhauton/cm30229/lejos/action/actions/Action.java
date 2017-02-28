package com.domhauton.cm30229.lejos.action.actions;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
public enum Action {
  FORWARD(new MoveForward()),
  FORWARD_STEP(new ForwardStep()),
  BACKWARD(new MoveBackward()),
  BACKWARD_STEP(new BackwardStep()),
  ROTATE_LEFT(new RotateLeft()),
  REVERSE_ROTATELEFT(new ReverseRotateLeft()),
  ROTATE_RIGHT(new RotateRight()),
  REVERSE_ROTATERIGHT(new ReverseRotateRight()),
  ROTATE_180(new Rotate180()),
  IDLE(new Idle());

  private ExecutableAction executableAction;

  Action(ExecutableAction executableAction) {
    this.executableAction = executableAction;
  }

  public ExecutableAction getExecutableAction() {
    return executableAction;
  }
}
