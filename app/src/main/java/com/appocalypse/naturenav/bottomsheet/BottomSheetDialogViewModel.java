package com.appocalypse.naturenav.bottomsheet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appocalypse.naturenav.api.POI;

import java.util.List;

public class BottomSheetDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> displayingListMutableLiveData = new MutableLiveData<>(true);

    private final MutableLiveData<Boolean> visibleMutableLiveData = new MutableLiveData<>(false);

    BottomSheetDialogViewModel() {

    }

    public void setDisplayingList(Boolean displayingList) {
        displayingListMutableLiveData.setValue(displayingList);
    }

    public LiveData<Boolean> getDisplayingListLiveData() {
        return displayingListMutableLiveData;
    }

    public LiveData<Boolean> getVisibleLiveData() {
        return visibleMutableLiveData;
    }

    public void setVisible(boolean visible) {
        this.visibleMutableLiveData.setValue(visible);
    }
}
