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
            Map.of(
                    "drinking_water", R.string.drinking_water_amenity,
                    "restaurant", R.string.restaurant_amenity,
                    "bicycle_parking", R.string.bicycle_parking_amenity,
                    "hospital", R.string.hospital_amenity,
                    "car_wash", R.string.car_wash_amenity,
                    "bicycle_repair_station", R.string.bicycle_repair_station_amenity,
                    "bar", R.string.bar_amenity
            ));
    public final static Integer[] amenityStringIds = {R.string.drinking_water_amenity, R.string.restaurant_amenity,
            R.string.bicycle_parking_amenity, R.string.hospital_amenity, R.string.car_wash_amenity,
            R.string.bicycle_repair_station_amenity, R.string.bar_amenity};

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
