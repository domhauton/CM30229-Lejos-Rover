package com.domhauton.cm30229.lejos.panel;

import lejos.nxt.Button;

/**
 * Created by dominic on 08/02/17.
 */
public enum ButtonType {
  LEFT, RIGHT, MENU, EXIT, INVALID;

  static ButtonType getFromNxtButton(Button button) {
    if (button.equals(Button.ENTER)) {
      return MENU;
    } else if (button.equals(Button.ESCAPE)) {
      return EXIT;
    } else if (button.equals(Button.LEFT)) {
      return LEFT;
    } else if (button.equals(Button.RIGHT)) {
      return RIGHT;
    } else {
      return INVALID;
    }
  }
}
