
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
    private MutableLiveData<List<ProductModel>> productList = new MutableLiveData<>();


    private final MutableLiveData<List<ProductModel>> _currentProductList = new MutableLiveData<>();
    public LiveData<List<ToyModel>> getCurrentToyList() {
        return _currentToyList;
    }

    public LiveData<List<ProductModel>> getCurrentProductList() {
        return _currentProductList;
    }

    private final MutableLiveData<Integer> currentPopularLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> currentSearchLiveData = new MutableLiveData<>();

    @Inject
    public ProductViewModel() {
        currentPopularLiveData.setValue(2);
        currentSearchLiveData.setValue("");
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
    }

    public void setCurrentPopular(int id) {
        currentPopularLiveData.setValue(id);
    }

    public void setCurrentSearchValue(String query) {
        currentSearchLiveData.setValue(query);
    }

    public MutableLiveData<String> getCurrentSearchLiveData() {
        return currentSearchLiveData;
    }
    public LiveData<List<ProductModel>> getProductList() {
        return productList;
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
                .filter(p -> {
                    boolean matchesQuery = p.getName().toLowerCase().contains(query.toLowerCase());
                                        return matchesQuery;
                })
                .collect(Collectors.toList());
        _currentProductList.setValue(filteredList);
    }
}
