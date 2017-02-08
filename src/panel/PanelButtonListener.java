package panel;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

/**
 * Created by dominic on 08/02/17.
 */
public class PanelButtonListener implements ButtonListener {

    public PanelButtonListener() {
        Button.ENTER.addButtonListener(this);
        Button.LEFT.addButtonListener(this);
        Button.RIGHT.addButtonListener(this);
        Button.ESCAPE.addButtonListener(this);
    }

    @Override
    public void buttonPressed(Button b) {
        if(b.equals(Button.ENTER)) {
            System.out.println("Enter pressed.");
        } else if (b.equals(Button.ESCAPE)) {
            System.out.println("Escape pressed. Shutting down.");
            System.exit(1);
        } else if (b.equals(Button.LEFT)) {
            System.out.println("Left pressed.");
        } else if (b.equals(Button.RIGHT)) {
            System.out.println("Right pressed.");
        } else {
            System.err.println("Invalid button pressed in panel button listener");
        }
    }

    @Override
    public void buttonReleased(Button b) {
        if(b.equals(Button.ENTER)) {
            System.out.println("Enter released.");
        } else if (b.equals(Button.LEFT)) {
            System.out.println("Left released.");
        } else if (b.equals(Button.RIGHT)) {
            System.out.println("Right released.");
        }
    }
}
