package com.domhauton.cm30229.lejos.event.sonar;

import com.domhauton.cm30229.lejos.util.Proximity;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarEvent {
  private final Proximity leftProximity;
  private final Proximity rightProximity;
  private final Proximity forwardProximity;

  SonarEvent(Proximity leftProximity, Proximity rightProximity, Proximity forwardProximity) {
    this.leftProximity = leftProximity;
    this.rightProximity = rightProximity;
    this.forwardProximity = forwardProximity;
  }

  public Proximity getLeftProximity() {
    return leftProximity;
  }

  public Proximity getRightProximity() {
    return rightProximity;
  }

  public Proximity getForwardProximity() {
    return forwardProximity;
  }
}
