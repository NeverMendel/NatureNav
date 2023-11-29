package com.appocalypse.naturenav.suggestionlist;

import android.widget.AdapterView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuggestionListViewModel extends ViewModel {

    private final MutableLiveData<String> searchStringMutableLiveData = new MutableLiveData<>("");
    private OnSuggestionClick onSuggestionClick;

    public interface OnSuggestionClick {
        void onClick(String suggestion);
    }

    SuggestionListViewModel(){

    }

    public void setOnSuggestionClick(OnSuggestionClick onSuggestionClick) {
        this.onSuggestionClick = onSuggestionClick;
    }

    public OnSuggestionClick getOnSuggestionClick() {
        return onSuggestionClick;
    }

    public void setSearchString(String searchString) {
        searchStringMutableLiveData.setValue(searchString);
    }

    LiveData<String> getSearchStringLiveData(){
        return searchStringMutableLiveData;
    }
}