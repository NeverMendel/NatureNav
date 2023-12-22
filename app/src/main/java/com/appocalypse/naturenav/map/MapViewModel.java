package com.appocalypse.naturenav.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class MapViewModel extends ViewModel {

    public MapViewModel() {

    }
    private MutableLiveData<POI> highlightRouteRequest = new MutableLiveData<>();

    public LiveData<POI> getHighlightRouteRequest() {
        return highlightRouteRequest;
    }

    public void requestHighlightRoute(POI poi) {
        highlightRouteRequest.setValue(poi);
    }

}