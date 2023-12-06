package com.appocalypse.naturenav.poiinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

public class PoiInfoViewModel extends ViewModel {
    private final MutableLiveData<POI> displayedPoiMutableLiveData = new MutableLiveData<>();

    public void setDisplayedPoi(POI displayedPoi) {
        displayedPoiMutableLiveData.setValue(displayedPoi);
    }

    public LiveData<POI> getDisplayedPoiLiveData() {
        return displayedPoiMutableLiveData;
    }
}
