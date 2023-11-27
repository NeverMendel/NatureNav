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

    // TODO: change this class as we need

    public String getAddress(Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

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

        return context.getString(R.string.address_not_available);
    }

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
