package com.domhauton.cm30229.lejos.event.sonar;


import com.domhauton.cm30229.lejos.RobotManager;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarEventCallback {
    private RobotManager robotManager;

    public SonarEventCallback(RobotManager robotManager) {
        this.robotManager = robotManager;
    }

    void sendSonarEvent(SonarEvent sonarEvent) {

    }
}
