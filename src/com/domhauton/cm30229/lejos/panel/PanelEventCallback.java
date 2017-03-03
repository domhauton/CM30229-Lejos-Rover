package com.domhauton.cm30229.lejos.panel;

import com.domhauton.cm30229.lejos.controller.RoverManager;

/**
 * Created by dominic on 10/02/17.
 */
public class PanelEventCallback {
  private final RoverManager roverManager;

  public PanelEventCallback(RoverManager roverManager) {
    this.roverManager = roverManager;
  }

  public void sendPanelEvent(ButtonType buttonType) {
    roverManager.panelButtonEvent(buttonType);
  }
}
