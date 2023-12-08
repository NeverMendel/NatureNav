package com.appocalypse.naturenav;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.appocalypse.naturenav.utility.GeocoderSingleton;
import com.appocalypse.naturenav.utility.PoiFinder;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
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

    public static Road calculateRoadDistance(Context context, GeoPoint geoPoint) {
        GeoPoint currentLocation = PoiFinder.getInstance().getLocation();

        Log.d("DistanceFinder", "Current Location: " + currentLocation);
        Log.d("DistanceFinder", "Destination Location: " + geoPoint);


        RoadManager roadManager = new OSRMRoadManager(context, "NatureNav");
        ArrayList<GeoPoint> waypoints = new ArrayList<>();
        waypoints.add(currentLocation);
        waypoints.add(geoPoint);

        Road road = roadManager.getRoad(waypoints);

        if (road.mStatus == Road.STATUS_OK) {
            // the distance is in meters
            Log.d("DistanceFinder", "Road distance: " + road.mLength);
            return road;
        } else {
            Log.d("DistanceFinder", "Road distance: " + road.mStatus);
            return null;
        }
    }
}
