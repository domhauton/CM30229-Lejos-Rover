package com.domhauton.cm30229.lejos.action.actions;


/**
 * Created by Dominic Hauton on 21/02/17.
 */
public enum Action {
  FORWARD(new MoveForward()),
  //  ROTATE_SMALL_LEFT(new RotateSmallLeft()),
//  ROTATE_SMALL_RIGHT(new RotateSmallRight()),
//  ROTATE_180(new Rotate180()),
  ARC_LEFT(new ArcLeft()),
  ARC_RIGHT(new ArcRight()),
  BACKWARD_AND_TURN_RIGHT(new ExecutableAction[]{new SmallBackward(), new RotateSmallRight()}),
  BACKWARD_AND_TURN_LEFT(new ExecutableAction[]{new SmallBackward(), new RotateSmallLeft()}),
  FORWARD_AND_TURN_RIGHT(new ExecutableAction[]{new SmallForward(), new RotateSmallRight()}),
  FORWARD_AND_TURN_LEFT(new ExecutableAction[]{new SmallForward(), new RotateSmallLeft()}),
  IDLE(new Idle());

  private ExecutableAction executableAction;

  Action(ExecutableAction executableAction) {
    this.executableAction = executableAction;
  }

  Action(ExecutableAction[] executableAction) {
    this.executableAction = new CompositeAction(executableAction);
  }

  public ExecutableAction getExecutableAction() {
    return executableAction;
  }
}
