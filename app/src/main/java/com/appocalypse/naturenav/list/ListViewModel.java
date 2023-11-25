package com.appocalypse.naturenav.list;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.OverpassTurboPOIProvider;
import com.appocalypse.naturenav.api.POI;


import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ListViewModel extends ViewModel implements IMyLocationConsumer {
    private static final String TAG = "ListViewModel";

    private final MutableLiveData<ArrayList<POI>> pois = new MutableLiveData<>(new ArrayList<>());
    GpsMyLocationProvider locationProvider;
    GeoPoint location;

    public ListViewModel() {
    }

    public void search(String query) {
        if(location == null) {
            Log.e(TAG, "error: location is null");
            return;
        }
        Executors.newSingleThreadExecutor().execute(() -> {
            OverpassTurboPOIProvider poiProvider = new OverpassTurboPOIProvider();
            Log.i(TAG, "location latitude: " + location.getLatitude() + ", location longitude: " + location.getLongitude());
            try {
                ArrayList<POI> poiArrayList = poiProvider.getPOICloseTo(location, query, 25, 5000);
                pois.postValue(poiArrayList);
                Log.i(TAG, "search: " + poiArrayList.toString());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        });
    }

    public LiveData<ArrayList<POI>> getData() {
        return pois;
    }

    public void setContext(Context context){
        if(this.locationProvider == null){
            this.locationProvider = new GpsMyLocationProvider(context);
            this.locationProvider.startLocationProvider(this);
        }
    }

    @Override
    public void onLocationChanged(Location location, IMyLocationProvider source) {
        this.location = new GeoPoint(location.getLatitude(), location.getLongitude());
    }

    public GeoPoint getLocation() {
        return location;
    }
}
