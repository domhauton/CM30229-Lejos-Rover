package com.domhauton.cm30229.lejos.controller;

import com.domhauton.cm30229.lejos.action.Action;
import com.domhauton.cm30229.lejos.action.ActionManager;
import com.domhauton.cm30229.lejos.event.sensors.SensorEvent;
import com.domhauton.cm30229.lejos.event.sonar.SonarEvent;
import com.domhauton.cm30229.lejos.panel.ButtonType;
import com.domhauton.cm30229.lejos.panel.PanelEventCallback;
import com.domhauton.cm30229.lejos.state.Direction;
import com.domhauton.cm30229.lejos.state.RoverState;
import com.domhauton.cm30229.lejos.util.EventUtils;
import lejos.nxt.LCD;

/**
 * Created by dominic running 08/02/17.
 */
public class RoverManager implements Runnable {
    private final long loopTimeLength;
    private long nextLoopTime;
    private long loopCounter;
    private boolean running;
    private boolean movingForward;

    private RoverState roverState;
    private ShutdownCallback shutdownCallback;
    private ActionManager actionManager;

    public RoverManager(long planRate) {
        loopTimeLength = 1000L/planRate;
        roverState = new RoverState();
    }

    public void panelButtonEvent(ButtonType e) {
        if(e.equals(ButtonType.EXIT)) {
            running = false;
            if(shutdownCallback != null) {
                shutdownCallback.shutDownSensors();
            }
        }
        
        if (e.equals(ButtonType.MENU)) {
        	if (!movingForward) {
        		movingForward = true;
        		actionManager.executeAction(Action.FORWARD);
        	} else {
        		movingForward = false;
        		actionManager.executeAction(Action.STOP);
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
    	movingForward = false;
        running = true;
        while(running) {
            nextLoopTime = EventUtils.rateLimitSleep(nextLoopTime, loopTimeLength);
            printState();
            loopCounter++;
        }
    }

    private void printState() {
        //LCD.clear();
        int row = 0;
        LCD.drawString("Loop: " + loopCounter, 0, row++);
        for(Direction direction: Direction.values()) {
            String printString = direction.toString() + ": " + roverState.getProximity(direction).toString() + "  ";
            LCD.drawString(printString,0,row++);
        }
    }

    public void setShutdownCallback(ShutdownCallback shutdownCallback) {
        this.shutdownCallback = shutdownCallback;
    }
    
    public void setMovingForward(boolean movingForward) {
    	this.movingForward = movingForward;
    }
}
