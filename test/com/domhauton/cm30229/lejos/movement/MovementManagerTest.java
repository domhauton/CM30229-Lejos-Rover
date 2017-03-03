package com.domhauton.cm30229.lejos.movement;

import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.state.Direction;
import com.domhauton.cm30229.lejos.state.RoverState;
import com.domhauton.cm30229.lejos.util.Proximity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by Dominic Hauton on 26/02/17.
 */
class MovementManagerTest {

  private RoverState normalState;
  private RoverState crashedFrontState;
  private RoverState crashedBackState;

  @BeforeEach
  void setUp() throws Exception {
    normalState = new RoverState()
            .toggleMoving();
    crashedFrontState = new RoverState()
            .toggleMoving()
            .setProximity(Direction.FRONT, Proximity.NEAR, true);
    crashedBackState = new RoverState()
            .toggleMoving()
            .setProximity(Direction.FRONT, Proximity.NEAR, true);
  }

  @Test
  void idleWhenInactive() throws Exception {
    Action plannedAction = MovementManager.planAction(new RoverState(), null);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void moveForwardWhenNormal() throws Exception {
    Action plannedAction = MovementManager.planAction(normalState, null);

    Assertions.assertEquals(Action.FORWARD, plannedAction);
  }

  @Test
  void idleWhenFrontCrash() throws Exception {
    Action plannedAction = MovementManager.planAction(crashedFrontState, null);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void idleWhenBackCrash() throws Exception {
    Action plannedAction = MovementManager.planAction(crashedBackState, null);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

}