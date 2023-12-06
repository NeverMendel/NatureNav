package com.appocalypse.naturenav.map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

import java.util.List;

public class MapViewModel extends ViewModel {

    private MutableLiveData<List<POI>> poiLiveData = new MutableLiveData<>();

    public MapViewModel() {

    }

    public MutableLiveData<List<POI>> getPOILiveData() {
        return poiLiveData;
    }

    public void setPoiLiveData(List<POI> poi) {
        poiLiveData.setValue(poi);
    }
}