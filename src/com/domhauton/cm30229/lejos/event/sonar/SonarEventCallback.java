package com.domhauton.cm30229.lejos.event.sonar;


import com.domhauton.cm30229.lejos.controller.RoverManager;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarEventCallback {
    private RoverManager roverManager;

    public SonarEventCallback(RoverManager roverManager) {
        this.roverManager = roverManager;
    }

    void sendSonarEvent(SonarEvent sonarEvent) {
        roverManager.sonarEvent(sonarEvent);
    }
}
