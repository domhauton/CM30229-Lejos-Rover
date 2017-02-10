package com.domhauton.cm30229.lejos.event;

/**
 * Created by dominic on 10/02/17.
 */
public enum Proximity {
    NEAR, MID, FAR;

    public static Proximity getProximity(double rawValue, double nearCap, double midCap) {
        if(rawValue < nearCap) {
            return Proximity.NEAR;
        } else if (rawValue < midCap) {
            return Proximity.MID;
        } else {
            return Proximity.FAR;
        }
    }
}
