package com.domhauton.cm30229.lejos;

import com.domhauton.cm30229.lejos.action.ActionManager;
import com.domhauton.cm30229.lejos.controller.RoverManager;
import com.domhauton.cm30229.lejos.controller.SensorCallback;
import com.domhauton.cm30229.lejos.event.sensors.SensorEventCallback;
import com.domhauton.cm30229.lejos.event.sensors.SensorRunner;
import com.domhauton.cm30229.lejos.event.sonar.SonarEventCallback;
import com.domhauton.cm30229.lejos.event.sonar.SonarRunner;
import com.domhauton.cm30229.lejos.panel.PanelButtonListener;
import com.domhauton.cm30229.lejos.panel.PanelEventCallback;

public class Main {

  public static void main(String[] args) {
    ActionManager actionManager = new ActionManager();

    RoverManager roverManager = new RoverManager(10, actionManager);
    Thread roverManagerThread = new Thread(roverManager);

    PanelEventCallback panelEventCallback = new PanelEventCallback(roverManager);
    new PanelButtonListener(panelEventCallback);

    SensorEventCallback sensorEventCallback = new SensorEventCallback(roverManager);
    SensorRunner sensorRunner = new SensorRunner(30, sensorEventCallback);


    SonarEventCallback sonarEventCallback = new SonarEventCallback(roverManager);
    SonarRunner sonarRunner = new SonarRunner(1, sonarEventCallback);


    SensorCallback sensorCallback = new SensorCallback(sensorRunner, sonarRunner);
    roverManager.setSensorCallback(sensorCallback);


    roverManagerThread.start();
  }
}
