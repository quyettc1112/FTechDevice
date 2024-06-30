package com.example.ftechdevice.UI.Fragment.ProductFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.Model.ToyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductViewModel extends ViewModel {
    private final List<ToyModel> _fullToyList = new ArrayList<>();
    private final List<ProductModel> _fullProductList = new ArrayList<>();

    private final MutableLiveData<List<ToyModel>> _currentToyList = new MutableLiveData<>();
    private final MutableLiveData<List<ProductModel>> _currentProductList = new MutableLiveData<>();
    private final MutableLiveData<List<ProductModel>> _filteredProductList = new MutableLiveData<>();

    private final MutableLiveData<Integer> currentPopularLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> currentSearchLiveData = new MutableLiveData<>();

    @Inject
    public ProductViewModel() {
        currentPopularLiveData.setValue(2);
        currentSearchLiveData.setValue("");
    }

    public LiveData<List<ToyModel>> getCurrentToyList() {
        return _currentToyList;
    }

    public LiveData<List<ProductModel>> getCurrentProductList() {
        return _currentProductList;
    }

    public LiveData<List<ProductModel>> getFilteredProductList() {
        return _filteredProductList;
    }

    public void setToyList(List<ToyModel> toyList) {
        _fullToyList.clear();
        _fullToyList.addAll(toyList);
        _currentToyList.setValue(_fullToyList);
    }

    public void setProductList(List<ProductModel> pList) {
        _fullProductList.clear();
        _fullProductList.addAll(pList);
        _currentProductList.setValue(_fullProductList);
        filterProductList(currentSearchLiveData.getValue());
    }

    public void setCurrentPopular(int id) {
        currentPopularLiveData.setValue(id);
    }

    public void setCurrentSearchValue(String query) {
        currentSearchLiveData.setValue(query);
        filterProductList(query);
    }

    public void filterToyList(String query) {
        List<ToyModel> filteredList = _fullToyList.stream()
                .filter(toy -> {
                    boolean matchesQuery = toy.getToyName().toLowerCase().contains(query.toLowerCase());
                    boolean matchesTypePopular = currentPopularLiveData.getValue() == 0 || toy.getTypePoppular() == currentPopularLiveData.getValue();
                    return matchesQuery && matchesTypePopular;
                })
                .collect(Collectors.toList());
        _currentToyList.setValue(filteredList);
    }

    public void filterProductList(String query) {
        List<ProductModel> filteredList = _fullProductList.stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        _filteredProductList.setValue(filteredList);
    }
}
