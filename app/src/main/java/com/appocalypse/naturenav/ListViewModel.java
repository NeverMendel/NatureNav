package com.appocalypse.naturenav;

import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private final String[] data = {"item 1", "item 2", "item 3"};

    public ListViewModel(){

    }

    public String[] getData() {
        return data;
    }
}
