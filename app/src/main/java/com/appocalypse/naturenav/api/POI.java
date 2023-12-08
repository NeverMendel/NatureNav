package com.appocalypse.naturenav.api;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.util.Map;

public class POI {
    public long id;
    public String type = "N/A";
    public double lat;
    public double lon;
    public Map<String, String> tags;
    public String address;
    public double airDistanceMeters;
    public double roadDistanceMeters;

    public double roadDistanceSeconds; //

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
}
