package com.appocalypse.naturenav.utility;

import java.text.DecimalFormat;

public class UnitConverter {
    public static String formatDistance(double distanceMeters){
        String distance, unit;
        if(distanceMeters >= 1000.0){
            distance = formatOneSignificantDigit(distanceMeters / 1000.0);
            unit = "km";
        } else {
            distance = formatOneSignificantDigit(distanceMeters);
            unit = "m";
        }
        return distance + " " + unit;
    }

    public static String formatDuration(double durationSeconds){
        String duration, unit;
        if(durationSeconds >= 60.0){
            duration = formatOneSignificantDigit(durationSeconds / 60.0);
            unit = "m";
        } else {
            duration = formatOneSignificantDigit(durationSeconds);
            unit = "s";
        }
        return duration + " " + unit;
    }

    public static String formatOneSignificantDigit(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(number);
    }
}
