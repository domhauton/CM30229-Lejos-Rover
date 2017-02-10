package com.domhauton.cm30229.lejos.util;

/**
 * Created by dominic on 10/02/17.
 */
public enum Proximity {
    NEAR, MID, FAR;

    public static Proximity getProximityIncreasing(double rawValue, double nearCap, double midCap) {
        if(rawValue < nearCap) {
            return Proximity.NEAR;
        } else if (rawValue < midCap) {
            return Proximity.MID;
        } else {
            return Proximity.FAR;
        }
    }

    public static Proximity getProximityDecreasing(double rawValue, double nearCap, double midCap) {
        if(rawValue > nearCap) {
            return Proximity.NEAR;
        } else if (rawValue > midCap) {
            return Proximity.MID;
        } else {
            return Proximity.FAR;
        }
    }
}