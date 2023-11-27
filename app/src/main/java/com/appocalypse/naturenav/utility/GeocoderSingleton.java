package com.appocalypse.naturenav.utility;

import android.content.Context;
import android.location.Geocoder;

import java.util.Locale;

public class GeocoderSingleton {
    private static Geocoder instance;

    private GeocoderSingleton() {
    }

    public static Geocoder getInstance() {
        if (instance == null) throw new RuntimeException("geocoder was not instantiated");
        return instance;
    }

    public static Geocoder getInstance(Context context) {
        if (instance == null) instance = new Geocoder(context, Locale.getDefault());
        return instance;
    }
}
