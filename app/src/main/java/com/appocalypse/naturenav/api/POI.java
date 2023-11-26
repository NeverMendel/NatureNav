package com.appocalypse.naturenav.api;

import android.graphics.Bitmap;

import org.osmdroid.util.GeoPoint;

import java.util.Map;

public class POI {
    public long id;
    public double lat; // Modificato il tipo da long a double per corrispondere ai dati JSON
    public double lon; // Modificato il tipo da long a double per corrispondere ai dati JSON
    public String type = "Null";
    public Bitmap mThumbnail;

    public GeoPoint location;
    public Map<String, String> tags;

    // Aggiungi un costruttore che accetta latitudine e longitudine

    //fai diversi costruttori a matriosca
    public POI(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.location = new GeoPoint(lat, lon);
    }
    public POI(long id, double lat, double lon, String type){
        this(id,lat,lon);
        this.type = type;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
    @Override
    public String toString() {
        return "POI{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", type='" + type + '\'' +
                ", description='" + tags.get("description") + '\'' +
                ", mThumbnail=" + mThumbnail +
                ", location=" + location +
                ", tags=" + tags +
                '}';
    }
}
