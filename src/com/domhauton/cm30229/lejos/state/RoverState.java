package com.domhauton.cm30229.lejos.state;

import com.domhauton.cm30229.lejos.util.Proximity;

import java.util.Arrays;

/**
 * Created by dominic on 10/02/17.
 */
public class RoverState {
    private final Proximity[] proximities;
    private boolean active;
    private Direction wallPriority;

    public RoverState() {
        proximities = new Proximity[Direction.values().length];
        Arrays.fill(proximities, Proximity.FAR);
        wallPriority = Direction.FRONT; //defaults to follow neither wall
    }

    public synchronized RoverState setProximity(Direction direction, Proximity proximity) {
        proximities[direction.ordinal()] = proximity;
        return this;
    }

    public synchronized Proximity getProximity(Direction direction) {
        return proximities[direction.ordinal()];
    }
    
    public RoverState setActive(boolean active) {
    	this.active = active;
    	return this;
    }

    public RoverState toggleActive() {
        this.active = !this.active;
        return this;
    }

    public boolean isActive() {
    	return active;
    }
    
    public void setWallPriority(Direction wallPriority) {
    	this.wallPriority = wallPriority;
    }
    
    public Direction getWallPriority() {
    	return wallPriority;
    }
}
