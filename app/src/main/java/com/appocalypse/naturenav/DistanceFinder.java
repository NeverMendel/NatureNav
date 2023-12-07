package com.appocalypse.naturenav;

import com.appocalypse.naturenav.utility.PoiFinder;

import org.osmdroid.util.GeoPoint;

public class DistanceFinder {

    /**
     * Calculates the air distance from the current position to the given GeoPoint.
     *
     * @param geoPoint, GeoPoint to calculate the distance to
     * @return double, distance in meters
     */
    public static double calculateAirDistance(GeoPoint geoPoint) {
        return PoiFinder.getInstance().getLocation().distanceToAsDouble(geoPoint);
    }

    public static double calculateRoadDistance(GeoPoint geoPoint) {
        return 0; // TODO
    }
}
