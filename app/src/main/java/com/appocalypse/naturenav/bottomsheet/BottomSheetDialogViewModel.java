package com.appocalypse.naturenav.bottomsheet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

import java.util.List;

public class BottomSheetDialogViewModel extends ViewModel {

    MutableLiveData<Boolean> displayingListMutableLiveData = new MutableLiveData<>(true);

    BottomSheetDialogViewModel() {

    }

    public void setDisplayingList(Boolean displayingList) {
        displayingListMutableLiveData.setValue(displayingList);
    }

    public LiveData<Boolean> getDisplayingListLiveData() {
        return displayingListMutableLiveData;
    }
}
