package com.domhauton.cm30229.lejos.state;

import com.domhauton.cm30229.lejos.util.Proximity;

import java.util.Arrays;

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
  private Direction moveWithWallOn;

  // Planning state

  public RoverState() {
    proximityDirections = new Proximity[Direction.values().length];
    Arrays.fill(proximityDirections, Proximity.FAR);
    moveWithWallOn = Direction.LEFT;
    activitySelectionActive = false;
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

  public Direction getMoveWithWallOn() {
    return moveWithWallOn;
  }

  public void setMoveWithWallOn(Direction moveWithWallOn) {
    this.moveWithWallOn = moveWithWallOn;
  }

  public Proximity getWallPriorityProximity() {
    return getProximity(moveWithWallOn);
  }

  public boolean isCrashed() {
    return getProximity(Direction.FRONT).equals(Proximity.NEAR) || getProximity(Direction.BACK).equals(Proximity.NEAR);
  }
}
