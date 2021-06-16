package com.dtl.parks.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ParkViewModel extends ViewModel {
    private final MutableLiveData<Park> selectedPark = new MutableLiveData<>();
    private final MutableLiveData<List<Park>> selectedParks = new MutableLiveData<>();
    private final MutableLiveData<String> stateCodeUrl = new MutableLiveData<>();

    public void setSelectedPark(Park park){
        selectedPark.setValue(park);
    }
    public LiveData<Park> getSelectedPark(){
        return selectedPark;
    }
    public void setSelectedParks(List<Park> parks){
        selectedParks.setValue(parks);
    }
    public LiveData<List<Park>> getSelectedParks(){
        return selectedParks;
    }

    public MutableLiveData<String> getStateCodeUrl() {
        return stateCodeUrl;
    }

    public void setCde(String completeURL) {
        stateCodeUrl.setValue(completeURL);
    }
}
