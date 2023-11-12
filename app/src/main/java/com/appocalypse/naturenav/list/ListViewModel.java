package com.appocalypse.naturenav.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;

public class ListViewModel extends ViewModel {
    private static final String TAG = "ListViewModel";

    private final MutableLiveData<ArrayList<POI>> pois = new MutableLiveData<>(new ArrayList<>());

    public ListViewModel() {
    }

    public void search(GeoPoint location, String query) {
        Executors.newSingleThreadExecutor().execute(() -> {
            NominatimPOIProvider poiProvider = new NominatimPOIProvider("NatureNav");
            try {
                ArrayList<POI> poiArrayList = poiProvider.getPOICloseTo(location, query, 50, 5);
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
}
