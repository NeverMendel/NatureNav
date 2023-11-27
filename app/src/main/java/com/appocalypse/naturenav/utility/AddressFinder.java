package com.appocalypse.naturenav.utility;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class AddressFinder {

    public static String getAddress(double lat, double lon) {
        Geocoder geocoder = GeocoderSingleton.getInstance();

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                // Construct the address string
                StringBuilder addressBuilder = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressBuilder.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        addressBuilder.append(", ");
                    }
                }

                return addressBuilder.toString();
            }
        } catch (IOException e) {
            Log.e("POI", "Geocoding error: ", e);
        }

        return null;
    }
}
