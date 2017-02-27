package com.domhauton.cm30229.lejos.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.state.Direction;
import com.domhauton.cm30229.lejos.state.RoverState;
import com.domhauton.cm30229.lejos.util.Proximity;

/**
 * Created by Dominic Hauton on 21/02/17.
 */
class RoverManagerTest {
  private RoverManager roverManager;

  private RoverState normalState;
  private RoverState crashedFrontState;
  private RoverState crashedBackState;

  @Before
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

    Assert.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void moveForwardWhenNormal() throws Exception {
    Action plannedAction = roverManager.planAction(normalState);

    Assert.assertEquals(Action.FORWARD, plannedAction);
  }

  @Test
  void idleWhenFrontCrash() throws Exception {
    Action plannedAction = roverManager.planAction(crashedFrontState);

    Assert.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void idleWhenBackCrash() throws Exception {
    Action plannedAction = roverManager.planAction(crashedBackState);

    Assert.assertEquals(Action.IDLE, plannedAction);
  }
}