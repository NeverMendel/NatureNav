package com.appocalypse.naturenav.api;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appocalypse.naturenav.R;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class POI {
    public long id;
    public String type = "N/A";
    public double lat;
    public double lon;
    public Map<String, String> tags;
    public String address;

    public GeoPoint getGeopoint() {
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
