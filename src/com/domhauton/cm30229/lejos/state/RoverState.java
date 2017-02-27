package com.domhauton.cm30229.lejos.state;

import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.movement.Movement;
import com.domhauton.cm30229.lejos.util.Proximity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dominic on 10/02/17.
 */
public class RoverState {
  private final Proximity[] proximityDirections;
  private Proximity frontProximityLight;
  private Proximity frontProximitySonar;
  private boolean isMoving;

  // Menu state
  private boolean activitySelectionActive;
  private Direction circumnavigationDirection;

  // Planning state
  private Movement currentMovement;
  private LinkedList<Action> currentPlan;

  public RoverState() {
    proximityDirections = new Proximity[Direction.values().length];
    Arrays.fill(proximityDirections, Proximity.FAR);
    circumnavigationDirection = Direction.FRONT; //defaults to follow neither wall
    activitySelectionActive = false;
    currentMovement = Movement.IDLE;
    frontProximitySonar = Proximity.FAR;
    frontProximityLight = Proximity.FAR;
  }

  public synchronized RoverState setProximity(Direction direction, Proximity proximity, boolean isSonar) {
    if (direction == Direction.FRONT) {
      if (isSonar) {
        frontProximitySonar = proximity;
      } else {
        frontProximityLight = proximity;
      }
      proximity = frontProximityLight.getRank() < frontProximitySonar.getRank() ? frontProximityLight : frontProximitySonar;
    }
    proximityDirections[direction.ordinal()] = proximity;
    return this;
  }

  public synchronized Proximity getProximity(Direction direction) {
    return proximityDirections[direction.ordinal()];
  }

  public RoverState toggleMoving() {
    this.isMoving = !this.isMoving;
    return this;
  }

  public boolean isMoving() {
    return isMoving;
  }

  public boolean isActivitySelectionActive() {
    return activitySelectionActive;
  }

  public void setActivitySelectionActive(boolean activitySelectionActive) {
    this.activitySelectionActive = activitySelectionActive;
  }

  public Direction getCircumnavigationDirection() {
    return circumnavigationDirection;
  }

  public void setCircumnavigationDirection(Direction circumnavigationDirection) {
    this.circumnavigationDirection = circumnavigationDirection;
  }

  public Proximity getWallPriorityProximity() {
    return getProximity(circumnavigationDirection);
  }

  public Movement getCurrentMovement() {
    return currentMovement;
  }

  public void setCurrentMovement(Movement currentMovement) {
    this.currentMovement = currentMovement;
  }

  public boolean isCrashed() {
    return getProximity(Direction.FRONT).equals(Proximity.NEAR) || getProximity(Direction.BACK).equals(Proximity.NEAR);
  }

  public LinkedList<Action> getCurrentPlan() {
    return currentPlan;
  }

  public void addToPlan(List<Action> actionChain) {
    currentPlan.addAll(actionChain);
  }
}
