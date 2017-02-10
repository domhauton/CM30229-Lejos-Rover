package event;

import lejos.nxt.*;

/**
 * Created by dominic on 10/02/17.
 */
public class SonarRunner {
    private UltrasonicSensor ultrasonicSensor;
    private NXTRegulatedMotor swivelMotor;

    public SonarRunner() {
        ultrasonicSensor = new UltrasonicSensor(SensorPort.S4);
        swivelMotor = Motor.B;
    }
}
