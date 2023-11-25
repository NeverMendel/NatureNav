package com.appocalypse.naturenav.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class POI {
    public long id;
    public String type = "N/A";
    public GeoPoint location;
    public Map<String, String> tags;

    // TODO: change this class as we need

    public POI(long id, double lat, double lon) {
        this.id = id;
        this.location = new GeoPoint(lat, lon);
    }

    public POI(long id, double lat, double lon, String type) {
        this(id, lat, lon);
        this.type = type;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public String getAddress(Context context) {
        if (location != null) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

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
                Log.e("POI", "Errore durante la reverse geocoding", e);
            }

            return "Indirizzo non disponibile";
        } else {
            return "Posizione non disponibile";
        }
    }


    @Override
    public String toString() {
        return "POI{" +
                "id=" + id +
                ", lat=" + (location != null ? location.getLatitude() : "N/A") +
                ", lon=" + (location != null ? location.getLongitude() : "N/A") +
                ", type='" + type + '\'' +
                ", description='" + tags.get("description") + '\'' +
                ", location=" + location +
                ", tags=" + tags +
                '}';
    }


}
