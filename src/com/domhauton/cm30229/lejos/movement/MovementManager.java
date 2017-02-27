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

    Movement movement = roverState.isMoving() ? getNextMovement(roverState, sensorCallback) : Movement.IDLE;

    if (roverState.getMoveWithWallOn() == Direction.LEFT) { // Do things on the right
      switch (movement) {
        case BEARING_AWAY_WALL:
          return Action.ARC_RIGHT;
        case BEARING_TOWARDS_WALL:
          return Action.ARC_LEFT;
        case FOLLOWING_WALL:
          return Action.FORWARD;
        case CRASH_BACK:
          return Action.FORWARD_AND_TURN_LEFT;
        case CRASH_FRONT:
          return Action.BACKWARD_AND_TURN_RIGHT;
        case IDLE:
        default:
          return Action.IDLE;
      }
    } else if (roverState.getMoveWithWallOn() == Direction.RIGHT) { // To things to the left
      switch (movement) {
        case BEARING_AWAY_WALL:
          return Action.ARC_LEFT;
        case BEARING_TOWARDS_WALL:
          return Action.ARC_RIGHT;
        case FOLLOWING_WALL:
          return Action.FORWARD;
        case CRASH_BACK:
          return Action.FORWARD_AND_TURN_RIGHT;
        case CRASH_FRONT:
          return Action.BACKWARD_AND_TURN_LEFT;
        case IDLE:
        default:
          return Action.IDLE;
      }
    } else {
      return Action.IDLE;
    }
  }

  private static Movement getNextMovement(RoverState roverState, SensorCallback sensorCallback) {
    if (roverState.isCrashed()) {
      sensorCallback.setSonarPriority(Direction.FRONT);
      boolean isCrashedBothWays = roverState.getProximity(Direction.FRONT) == Proximity.NEAR &&
              roverState.getProximity(Direction.BACK) == Proximity.NEAR;
      if (isCrashedBothWays) { // GIVE UP
        return Movement.IDLE;
      } else if (roverState.getProximity(Direction.FRONT) == Proximity.NEAR) {
        return Movement.CRASH_FRONT;
      } else { // Must have crashed backwards
        return Movement.CRASH_BACK;
      }
    } else {
      Proximity proximityToWall = roverState.getWallPriorityProximity();
      switch (proximityToWall) {
        case FAR:
          sensorCallback.setSonarPriority(Direction.FRONT);
          return Movement.BEARING_TOWARDS_WALL;
        case MID:
          sensorCallback.setSonarPriority(roverState.getMoveWithWallOn());
          return Movement.FOLLOWING_WALL;
        case NEAR:
        default:
          sensorCallback.setSonarPriority(roverState.getMoveWithWallOn());
          return Movement.BEARING_AWAY_WALL;
      }
    }
  }
}
