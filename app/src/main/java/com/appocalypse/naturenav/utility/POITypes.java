package com.appocalypse.naturenav.utility;

import android.content.Context;

import com.appocalypse.naturenav.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POITypes {
    // TODO: Add all amenities that we're interested in
    // https://wiki.openstreetmap.org/wiki/Key:amenity
    public final static Map<String, Integer> amenityToStringId = new HashMap<>(
            Map.ofEntries(
                    Map.entry("drinking_water", R.string.drinking_water_amenity),
                    Map.entry("restaurant", R.string.restaurant_amenity),
                    Map.entry("bicycle_parking", R.string.bicycle_parking_amenity),
                    Map.entry("hospital", R.string.hospital_amenity),
                    Map.entry("car_wash", R.string.car_wash_amenity),
                    Map.entry("bicycle_repair_station", R.string.bicycle_repair_station_amenity),
                    Map.entry("bar", R.string.bar_amenity),
                    Map.entry("library", R.string.library_amenity),
                    Map.entry("school", R.string.school_amenity),
                    Map.entry("university", R.string.university_string),
                    Map.entry("bus_station", R.string.bus_station_amenity),
                    Map.entry("fuel", R.string.gas_station_amenity),
                    Map.entry("bank", R.string.bank_amenity),
                    Map.entry("cinema", R.string.cinema_amenity),
                    Map.entry("police", R.string.police_station_amenity),
                    Map.entry("toilets", R.string.toilet_amenity),
                    Map.entry("shower", R.string.shower_amenity),
                    Map.entry("parking", R.string.parking_amenity)
            ));

    public static List<String> getAmenityStrings(Context context) {
        Integer[] stringIds = new Integer[amenityToStringId.size()];
        stringIds = amenityToStringId.values().toArray(stringIds);
        ArrayList<String> amenities = new ArrayList<>(stringIds.length);
        for (int i = 0; i < stringIds.length; i++) {
            amenities.add(context.getString(stringIds[i]));
        }
        return amenities;
    }
}
