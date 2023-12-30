package com.appocalypse.naturenav.api;

import androidx.annotation.NonNull;

import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.util.GeoPoint;

import java.util.Comparator;
import java.util.Map;

public class POI {
    public long id;
    public String type = "N/A";
    public double lat;
    public double lon;
    public Map<String, String> tags;
    public String address;
    public double airDistanceMeters;
    public Road road;

    public GeoPoint getGeoPoint() {
        return new GeoPoint(lat, lon);
    }

    @NonNull
    @Override
    public String toString() {
        return "POI{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", tags=" + tags +
                '}';
    }

    public static class AirDistanceComparator implements Comparator<POI> {
        @Override
        public int compare(POI o1, POI o2) {
            return Double.compare(o1.airDistanceMeters, o2.airDistanceMeters);
        }
    }
}