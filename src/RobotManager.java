import panel.ButtonType;

/**
 * Created by dominic on 08/02/17.
 */
public class RobotManager implements Runnable {
    private final static int DECISION_LOOP_FREQ = 10;

    private final static long LOOP_LENGTH = 1000L/DECISION_LOOP_FREQ;
    private boolean on;

    public RobotManager(boolean on) {
        this.on = on;
    }

    public void panelButtonEvent(ButtonType e) {
        if(e.equals(ButtonType.EXIT)) {
            on = false;
        }
    }

    public void bumpSensorEvent() {

    }

    public void sonarEvent() {

    }

    public void proximityEvent() {

    }

    private void sendInstruction() {

    }

    @Override
    public void run() {
        while(on) {
            try {
                Thread.sleep(LOOP_LENGTH);
            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted");
            }
        }
    }
}
