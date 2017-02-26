package com.domhauton.cm30229.lejos.util;

import lejos.nxt.LCD;

/**
 * Created by dominic on 10/02/17.
 */
public abstract class EventUtils {

    public static long rateLimitSleep(long nextLoopTime, long loopTimeLength) {
        long waitTime = nextLoopTime - System.currentTimeMillis();
        if(waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                System.err.println("Sonar Runner sleep interrupted");
            }
        }
        return System.currentTimeMillis() + loopTimeLength;
    }

    public static double getAverageDistance(double[] measurements) {
        // Get  naive mean;

        double total = 0;
        for( double value : measurements) {
            total += value;
        }
        double mean = total/ (double) measurements.length;

        // Calculate standard deviation

        double variance = 0.0f;
        for(double value : measurements) {
            variance += Math.pow(value - mean, 2);
        }
        double sd = Math.sqrt(variance);

        // Prune values more than 2SD out from the mean.

        double cleanMean = 0.0f;
        int usefulValues = 0;
        double upperCap = mean + (2*sd);
        double lowerCap = mean - (2*sd);

        for(double value : measurements) {
            if(lowerCap < value && value < upperCap) {
                usefulValues++;
                cleanMean += value;
            }
        }

        // If everything pruned just return the naive mean

        if(usefulValues != 0) {
            return cleanMean/(double) usefulValues;
        } else {
            return mean;
        }
    }

    public static void debugDisplay1(String message) {
        LCD.clear(6);
        LCD.drawString(message,0, 6);
    }
    public static void debugDisplay2(String message) {
        LCD.clear(7);
        LCD.drawString(message,0, 7);
    }
}
