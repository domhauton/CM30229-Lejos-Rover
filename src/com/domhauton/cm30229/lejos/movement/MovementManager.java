package com.domhauton.cm30229.lejos.movement;

import com.domhauton.cm30229.lejos.action.actions.Action;
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
  public static Action planAction(RoverState roverState) {
    boolean shouldMove = roverState.isMoving() && !roverState.isCrashed();

    Proximity proximityToWall = roverState.getWallPriorityProximity();

    switch (proximityToWall) {
      case FAR:
        roverState.setCurrentMovement(Movement.BEARING_TOWARDS_WALL);
      case MID:
        roverState.setCurrentMovement(Movement.FOLLOWING_WALL);
      case NEAR:
        roverState.setCurrentMovement(Movement.BEARING_AWAY_WALL);
    }

    return shouldMove ? Action.FORWARD : Action.IDLE;
  }
}
