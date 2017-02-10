package com.domhauton.cm30229.lejos;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import com.domhauton.cm30229.lejos.panel.PanelButtonListener;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting rover.");
        new PanelButtonListener();
        Motor.A.flt();
        Motor.B.flt();
        Button.waitForAnyPress();
    }
}
