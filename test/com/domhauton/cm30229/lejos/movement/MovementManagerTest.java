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
            .setProximity(Direction.FRONT, Proximity.NEAR);
    crashedBackState = new RoverState()
            .toggleMoving()
            .setProximity(Direction.FRONT, Proximity.NEAR);
  }

  @Test
  void idleWhenInactive() throws Exception {
    Action plannedAction = MovementManager.planAction(new RoverState());

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void moveForwardWhenNormal() throws Exception {
    Action plannedAction = MovementManager.planAction(normalState);

    Assertions.assertEquals(Action.FORWARD, plannedAction);
  }

  @Test
  void idleWhenFrontCrash() throws Exception {
    Action plannedAction = MovementManager.planAction(crashedFrontState);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

  @Test
  void idleWhenBackCrash() throws Exception {
    Action plannedAction = MovementManager.planAction(crashedBackState);

    Assertions.assertEquals(Action.IDLE, plannedAction);
  }

}