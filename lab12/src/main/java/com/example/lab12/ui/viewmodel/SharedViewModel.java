package com.example.lab12.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Double> sharedData = new MutableLiveData<>(0.0);
    private final MutableLiveData<String> sharedOperation = new MutableLiveData<>("");
    public MutableLiveData<Double> getSharedData() {
        return sharedData;
    }
    public MutableLiveData<String> getSharedOperation() {
        return sharedOperation;
    }
    public void calculateNumber(double result, String operation) {
        if (sharedData.getValue() != null && sharedOperation.getValue() != null) {
            sharedData.setValue(result);
            sharedOperation.setValue(operation);
        } else {
            sharedData.setValue(0.0);
            sharedOperation.setValue("");
        }
    }
}
