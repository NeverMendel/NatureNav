package com.appocalypse.naturenav.poilist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

import java.util.ArrayList;
import java.util.List;

public class PoiListViewModel extends ViewModel {
    public static final String TAG = "PoiListViewModel";
    private final MutableLiveData<List<POI>> poiMutableLiveData = new MutableLiveData<>();

    public PoiListViewModel() {

    }

    public LiveData<List<POI>> getPoiLiveData() {
        return poiMutableLiveData;
    }

    public void setPoi(List<POI> poi) {
        this.poiMutableLiveData.setValue(poi);
    }

    public void clearPoi(){
        this.poiMutableLiveData.setValue(new ArrayList<>());
    }
}
