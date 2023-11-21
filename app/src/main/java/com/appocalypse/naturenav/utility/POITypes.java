package com.appocalypse.naturenav.utility;

import com.appocalypse.naturenav.R;

import java.util.HashMap;
import java.util.Map;

public class POITypes {
    // TODO: Add all amenities that we're interested in
    // https://wiki.openstreetmap.org/wiki/Key:amenity
    public final static Map<String, Integer> amenityToStringId = new HashMap<>(
            Map.of(
                    "drinking_water", R.string.drinking_water_amenity,
                    "restaurant", R.string.restaurant_amenity
            ));
}
