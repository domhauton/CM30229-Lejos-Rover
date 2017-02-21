package com.domhauton.cm30229.lejos.controller;

import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.state.Direction;
import com.domhauton.cm30229.lejos.state.RoverState;
import com.domhauton.cm30229.lejos.util.Proximity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
class RoverManagerTest {
  private RoverManager roverManager;

  private RoverState normalState;
  private RoverState crashedFrontState;
  private RoverState crashedBackState;

  @BeforeEach
  void setUp() throws Exception {
    roverManager = new RoverManager(10, null);

    normalState = new RoverState()
            .setActive(true);
    crashedFrontState = new RoverState()
            .setActive(true)
            .setProximity(Direction.FRONT, Proximity.NEAR);
    crashedBackState = new RoverState()
            .setActive(true)
            .setProximity(Direction.FRONT, Proximity.NEAR);
  }

  @Test
  void idleWhenInactive() throws Exception {
    Action plannedAction = roverManager.planAction(new RoverState());

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void moveForwardWhenNormal() throws Exception {
    Action plannedAction = roverManager.planAction(normalState);

    Assertions.assertEquals(Action.FORWARD, plannedAction);
  }

  @Test
  void idleWhenFrontCrash() throws Exception {
    Action plannedAction = roverManager.planAction(crashedFrontState);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void idleWhenBackCrash() throws Exception {
    Action plannedAction = roverManager.planAction(crashedBackState);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }
}