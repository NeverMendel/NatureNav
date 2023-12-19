package com.appocalypse.naturenav;

import android.content.Context;
import android.util.Log;

import com.appocalypse.naturenav.utility.PoiFinder;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

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

    public static Road getRoad(Context context, GeoPoint geoPoint) {
        GeoPoint currentLocation = PoiFinder.getInstance().getLocation();

        Log.d("DistanceFinder", "Current Location: " + currentLocation);
        Log.d("DistanceFinder", "Destination Location: " + geoPoint);

        RoadManager roadManager = new OSRMRoadManager(context, "NatureNav");
        ArrayList<GeoPoint> waypoints = new ArrayList<>();
        waypoints.add(currentLocation);
        waypoints.add(geoPoint);

        Road road = roadManager.getRoad(waypoints);

        Log.d("DistanceFinder", "Road status: " + road.mStatus);
        if (road.mStatus != Road.STATUS_OK) {
            return new Road();
        }
        return road;
    }
}
