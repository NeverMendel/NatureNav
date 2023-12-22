package com.appocalypse.naturenav.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

public class MapViewModel extends ViewModel {

    private MutableLiveData<POI> highlightRouteRequest = new MutableLiveData<>();

    public MapViewModel() {

    }

    public LiveData<POI> getHighlightRouteRequest() {
        return highlightRouteRequest;
    }

    public void requestHighlightRoute(POI poi) {
        highlightRouteRequest.setValue(poi);
    }

}