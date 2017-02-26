package com.domhauton.cm30229.lejos.controller;

import com.domhauton.cm30229.lejos.action.ActionManager;
import com.domhauton.cm30229.lejos.action.actions.Action;
import com.domhauton.cm30229.lejos.event.sensors.SensorEvent;
import com.domhauton.cm30229.lejos.event.sonar.SonarEvent;
import com.domhauton.cm30229.lejos.panel.ButtonType;
import com.domhauton.cm30229.lejos.state.Direction;
import com.domhauton.cm30229.lejos.state.RoverState;
import com.domhauton.cm30229.lejos.util.EventUtils;
import com.domhauton.cm30229.lejos.util.Proximity;
import lejos.nxt.LCD;

/**
 * Created by dominic running 08/02/17.
 */
public class RoverManager implements Runnable {
  private final long loopTimeLength;
  private final ActionManager actionManager;
  private long nextLoopTime;
  private long loopCounter;
  private boolean running;
  private RoverState roverState;
  private SensorCallback sensorCallback;

  public RoverManager(long planRate, ActionManager actionManager) {
    this.actionManager = actionManager;
    loopTimeLength = 1000L / planRate;
    roverState = new RoverState();
  }

  public void panelButtonEvent(ButtonType e) {

    if(roverState.isActivitySelectionActive() && !roverState.isActive()) { // Action Selection Menu
      roverState.setActivitySelectionActive(false);
      switch (e) {
        case LEFT:
          EventUtils.debugDisplay1("Cal Req");
          EventUtils.debugDisplay2("");
          sensorCallback.beginCalibration();
          EventUtils.debugDisplay1("Cal Done");
          break;
        case MENU:
          roverState.toggleActive();
          EventUtils.debugDisplay1("Following Wall");
          EventUtils.debugDisplay2(roverState.getWallPriority().toString());
          break;
        case EXIT:
          running = false;
          sensorCallback.shutDownSensors();
      }
    } else { // Main
      switch (e) {
        case EXIT:
          running = false;
          sensorCallback.shutDownSensors();
        case LEFT:
          roverState.setWallPriority(Direction.LEFT);
        case RIGHT:
          roverState.setWallPriority(Direction.RIGHT);
        case MENU:
          if(roverState.isActive()) {
            roverState.toggleActive();
            EventUtils.debugDisplay1("Stopped");
            EventUtils.debugDisplay2("");
          } else {
            roverState.setActivitySelectionActive(true);
            EventUtils.debugDisplay1("Press L to Cal");
            EventUtils.debugDisplay2("Press M to Run");
          }
      }
    }
  }

  public void sensorEvent(SensorEvent event) {
    roverState.setProximity(Direction.BACK, event.getBackProximity());
    roverState.setProximity(Direction.FRONT, event.getFrontProximity());
  }

  public void sonarEvent(SonarEvent event) {
    roverState.setProximity(Direction.LEFT, event.getLeftProximity());
    roverState.setProximity(Direction.RIGHT, event.getRightProximity());
  }

  @Override
  public void run() {
    EventUtils.debugDisplay1("Press M to Start");
    EventUtils.debugDisplay2("");
    running = true;
    sensorCallback.runSensors();
    while (running) {
      nextLoopTime = EventUtils.rateLimitSleep(nextLoopTime, loopTimeLength);
      Action nextAction = planAction(roverState);
      actionManager.executeAction(nextAction);
      printState();
      loopCounter++;
    }
  }

  /**
   * Decide on what action to take next.
   *
   * @return Next action to be taken given state.
   */
  Action planAction(RoverState currentRoverState) {
    boolean hasCrashed = currentRoverState.getProximity(Direction.FRONT).equals(Proximity.NEAR)
            || currentRoverState.getProximity(Direction.BACK).equals(Proximity.NEAR);

    boolean shouldMove = currentRoverState.isActive() && !hasCrashed;

    return shouldMove ? Action.FORWARD : Action.IDLE;
  }

  private void printState() {
    //LCD.clear();
    int row = 0;
    LCD.drawString("Loop: " + loopCounter, 0, row++);
    for (Direction direction : Direction.values()) {
      String printString = direction.toString() + ": " + roverState.getProximity(direction).toString() + "  ";
      LCD.drawString(printString, 0, row++);
    }
  }

  public void setSensorCallback(SensorCallback sensorCallback) {
    this.sensorCallback = sensorCallback;
  }

}
