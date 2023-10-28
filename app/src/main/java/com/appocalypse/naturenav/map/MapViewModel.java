package com.appocalypse.naturenav.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.osmdroid.util.GeoPoint;

public class MapViewModel extends ViewModel {

    GeoPoint location = new GeoPoint(0.0, 0.0);

    public MapViewModel() {

    }

    public void setLocation(GeoPoint location){
        this.location = location;
    }

    public GeoPoint getLocation(){
        return location;
    }
}