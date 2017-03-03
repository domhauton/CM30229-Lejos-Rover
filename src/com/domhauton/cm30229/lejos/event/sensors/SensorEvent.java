package com.domhauton.cm30229.lejos.event.sensors;

import com.domhauton.cm30229.lejos.util.Proximity;

/**
 * Created by dominic on 10/02/17.
 */
public class SensorEvent {
  private final Proximity frontProximity;
  private final Proximity backProximity;

  public SensorEvent(Proximity frontProximity, Proximity backProximity) {
    this.frontProximity = frontProximity;
    this.backProximity = backProximity;
  }

  public Proximity getFrontProximity() {
    return frontProximity;
  }

  public Proximity getBackProximity() {
    return backProximity;
  }
}
