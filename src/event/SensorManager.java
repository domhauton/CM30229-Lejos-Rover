package event;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

/**
 * Created by dominic on 10/02/17.
 */
public class SensorManager implements Runnable {
    private final long loopTimeLength;
    private long nextLoopTime = 0L;
    private long totalLoopTime = 0L;

    private TouchSensor touchSensorFront;
    private TouchSensor touchSensorBack;
    private LightSensor lightSensorFront;

    public SensorManager(int sensor_poll_rate) {
        loopTimeLength = 1000L/sensor_poll_rate;
        touchSensorFront = new TouchSensor(SensorPort.S2);
        touchSensorBack = new TouchSensor(SensorPort.S1);
        lightSensorFront = new LightSensor(SensorPort.S3);

    }


    @Override
    public void run() {

    }
}
