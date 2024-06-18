package com.example.ftechdevice.UI.Activity.MapActivity;


import androidx.lifecycle.MutableLiveData;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseViewModel;

public class MapsViewModels extends BaseViewModel {

    public MutableLiveData<String> nearBy = new MutableLiveData<>();

    public MapsViewModels() {
        nearBy.setValue("drugstore");
    }
}