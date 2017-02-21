package com.domhauton.cm30229.lejos.state;

import com.domhauton.cm30229.lejos.util.Proximity;

import java.util.Arrays;

/**
 * Created by dominic on 10/02/17.
 */
public class RoverState {
    private final Proximity[] proximities;
    private boolean movingForward;

    public RoverState() {
        proximities = new Proximity[Direction.values().length];
        Arrays.fill(proximities, Proximity.FAR);
    }

    public synchronized void setProximity(Direction direction, Proximity proximity) {
        proximities[direction.ordinal()] = proximity;
    }

    public synchronized Proximity getProximity(Direction direction) {
        return proximities[direction.ordinal()];
    }
    
    public void setMovingForward(boolean movingForward) {
    	this.movingForward = movingForward;
    }
    
    public boolean isMovingForward() {
    	return movingForward;
    }
}
