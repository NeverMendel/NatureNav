package com.appocalypse.naturenav.utility;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appocalypse.naturenav.api.OverpassTurboPOIProvider;
import com.appocalypse.naturenav.api.POI;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class PoiFinder implements IMyLocationConsumer {
    private static final String TAG = "PoiFinder";

    private static PoiFinder instance;

    private final MutableLiveData<ArrayList<POI>> pois = new MutableLiveData<>(new ArrayList<>());
    private final GpsMyLocationProvider locationProvider;
    private GeoPoint location;

    private PoiFinder(Context context){
        this.locationProvider = new GpsMyLocationProvider(context);
        this.locationProvider.startLocationProvider(this);
    }

    public static PoiFinder getInstance(){
        if (instance == null) throw new RuntimeException("PoiFinder was not instantiated");
        return instance;
    }

    public static PoiFinder getInstance(Context context){
        if(instance == null) instance = new PoiFinder(context);
        return instance;
    }

    public void search(Context context, String query) {
        if(location == null) {
            Log.e(TAG, "error: location is null");
            return;
        }
        Executors.newSingleThreadExecutor().execute(() -> {
            OverpassTurboPOIProvider poiProvider = new OverpassTurboPOIProvider();
            Log.i(TAG, "location latitude: " + location.getLatitude() + ", location longitude: " + location.getLongitude());
            try {
                ArrayList<POI> poiArrayList = poiProvider.queryPOICloseTo(context, location, query, 25, 5000);
                pois.postValue(poiArrayList);
                Log.i(TAG, "search: " + poiArrayList.toString());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        });
    }

    public void clearPois(){
        pois.postValue(new ArrayList<>());
    }

    public LiveData<ArrayList<POI>> getPoisLiveData() {
        return pois;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public GeoPoint getLocation() {
        return location;
    }

    @Override
    public void onLocationChanged(Location location, IMyLocationProvider source) {
        this.location = new GeoPoint(location.getLatitude(), location.getLongitude());
    }
}
