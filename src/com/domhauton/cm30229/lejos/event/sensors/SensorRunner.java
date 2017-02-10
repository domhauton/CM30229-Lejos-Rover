package com.domhauton.cm30229.lejos.event.sensors;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

/**
 * Created by dominic on 10/02/17.
 */
public class SensorRunner implements Runnable {
    private final long loopTimeLength;
    private long nextLoopTime = 0L;
    private long totalLoopTime = 0L;

    private TouchSensor touchSensorFront;
    private TouchSensor touchSensorBack;
    private LightSensor lightSensorFront;

    public SensorRunner(int sensorPollRate) {
        loopTimeLength = 1000L/sensorPollRate;
        touchSensorFront = new TouchSensor(SensorPort.S2);
        touchSensorBack = new TouchSensor(SensorPort.S1);
        lightSensorFront = new LightSensor(SensorPort.S3);

    }


    @Override
    public void run() {

    }
}
