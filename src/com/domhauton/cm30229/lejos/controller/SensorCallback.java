package com.domhauton.cm30229.lejos.controller;

import com.domhauton.cm30229.lejos.event.sensors.SensorRunner;
import com.domhauton.cm30229.lejos.event.sonar.SonarRunner;
import com.domhauton.cm30229.lejos.util.EventUtils;

/**
 * Created by dominic on 10/02/17.
 */
public class SensorCallback {
  private final SensorRunner sensorRunner;
  private final SonarRunner sonarRunner;
  private Thread sensorThread;
  private Thread sonarThread;

  public SensorCallback(SensorRunner sensorRunner, SonarRunner sonarRunner) {
    this.sensorRunner = sensorRunner;
    this.sonarRunner = sonarRunner;
  }

  void runSensors() {
    sensorThread = new Thread(sensorRunner);
    sonarThread = new Thread(sonarRunner);
    sensorThread.start();
    sonarThread.start();
  }

  void shutDownSensors() {
    sensorRunner.stop();
    sonarRunner.stop();
    try {
      sensorThread.join();
      sonarThread.join();
    } catch (InterruptedException e) {
      EventUtils.debugDisplay1("ERROR stopping");
      EventUtils.debugDisplay2("sensor runners");
    }
  }

  void beginCalibration() {
    shutDownSensors();
    sonarRunner.runCalibrationSequence();
    sensorRunner.runCalibrationSequence();
    runSensors();
  }
}
