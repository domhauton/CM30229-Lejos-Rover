package com.domhauton.cm30229.lejos.panel;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

/**
 * Created by dominic on 08/02/17.
 */
public class PanelButtonListener implements ButtonListener {
    private final PanelEventCallback panelEventCallback;

    public PanelButtonListener(PanelEventCallback panelEventCallback) {
        this.panelEventCallback = panelEventCallback;
        Button.ENTER.addButtonListener(this);
        Button.LEFT.addButtonListener(this);
        Button.RIGHT.addButtonListener(this);
        Button.ESCAPE.addButtonListener(this);
    }

    @Override
    public void buttonPressed(Button b) {
        ButtonType buttonType = ButtonType.getFromNxtButton(b);
        panelEventCallback.sendPanelEvent(buttonType);
    }

    @Override
    public void buttonReleased(Button b) {
        // IGNORE
    }
}
