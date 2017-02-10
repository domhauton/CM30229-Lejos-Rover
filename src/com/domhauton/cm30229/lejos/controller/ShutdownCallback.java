package com.domhauton.cm30229.lejos.controller;

import com.domhauton.cm30229.lejos.event.sensors.SensorRunner;
import com.domhauton.cm30229.lejos.event.sonar.SonarRunner;

/**
 * Created by dominic on 10/02/17.
 */
public class ShutdownCallback {
    private final SensorRunner sensorRunner;
    private final SonarRunner sonarRunner;

    public ShutdownCallback(SensorRunner sensorRunner, SonarRunner sonarRunner) {
        this.sensorRunner = sensorRunner;
        this.sonarRunner = sonarRunner;
    }

    void shutDownSensors() {
        sensorRunner.stop();
        sonarRunner.stop();
    }
}
