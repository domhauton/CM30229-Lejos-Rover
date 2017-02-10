package com.domhauton.cm30229.lejos.event.sensors;

import com.domhauton.cm30229.lejos.controller.RoverManager;

/**
 * Created by dominic on 10/02/17.
 */
public class SensorEventCallback {
    private final RoverManager roverManager;

    public SensorEventCallback(RoverManager roverManager) {
        this.roverManager = roverManager;
    }

    void sendSensorEvent(SensorEvent sensorEvent) {
        roverManager.sensorEvent(sensorEvent);
    }
}
