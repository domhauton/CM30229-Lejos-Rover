package com.domhauton.cm30229.lejos.state;

import com.domhauton.cm30229.lejos.util.Proximity;

import java.util.Arrays;

/**
 * Created by dominic on 10/02/17.
 */
public class RoverState {
  private final Proximity[] proximities;
  private boolean active;
  private boolean activitySelectionActive;
  private Direction wallPriority;

  public RoverState() {
    proximities = new Proximity[Direction.values().length];
    Arrays.fill(proximities, Proximity.FAR);
    wallPriority = Direction.FRONT; //defaults to follow neither wall
    activitySelectionActive = false;
  }

  public synchronized RoverState setProximity(Direction direction, Proximity proximity) {
    proximities[direction.ordinal()] = proximity;
    return this;
  }

  public synchronized Proximity getProximity(Direction direction) {
    return proximities[direction.ordinal()];
  }

  public RoverState toggleActive() {
    this.active = !this.active;
    return this;
  }

  public boolean isActive() {
    return active;
  }

  public RoverState setActive(boolean active) {
    this.active = active;
    return this;
  }

  public boolean isActivitySelectionActive() {
    return activitySelectionActive;
  }

  public void setActivitySelectionActive(boolean activitySelectionActive) {
    this.activitySelectionActive = activitySelectionActive;
  }

  public Direction getWallPriority() {
    return wallPriority;
  }

  public void setWallPriority(Direction wallPriority) {
    this.wallPriority = wallPriority;
  }
}
