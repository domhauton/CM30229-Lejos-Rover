package com.domhauton.cm30229.lejos.movement;

import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.controller.SensorCallback;
import com.domhauton.cm30229.lejos.state.Direction;
import com.domhauton.cm30229.lejos.state.RoverState;
import com.domhauton.cm30229.lejos.util.Proximity;

/**
 * Created by Dominic Hauton on 26/02/17.
 */
public abstract class MovementManager {

  /**
   * Decide on what action to take next.
   *
   * @return Next action to be taken given state.
   */
  public static Action planAction(RoverState roverState, SensorCallback sensorCallback) {
    boolean shouldMove = roverState.isMoving() && !roverState.isCrashed();

    Proximity proximityToWall = roverState.getWallPriorityProximity();

    switch (proximityToWall) {
      case FAR:
        roverState.setCurrentMovement(Movement.BEARING_TOWARDS_WALL);
        sensorCallback.setSonarPriority(Direction.FRONT);
        break;
      case MID:
        roverState.setCurrentMovement(Movement.FOLLOWING_WALL);
        sensorCallback.setSonarPriority(roverState.getCircumnavigationDirection());
        break;
      case NEAR:
        roverState.setCurrentMovement(Movement.BEARING_AWAY_WALL);
        sensorCallback.setSonarPriority(roverState.getCircumnavigationDirection());
        break;
    }

    return shouldMove ? Action.FORWARD : Action.IDLE;
  }
}
