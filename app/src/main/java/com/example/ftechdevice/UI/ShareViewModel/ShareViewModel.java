package com.example.ftechdevice.UI.ShareViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ftechdevice.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShareViewModel extends ViewModel {

    private final MutableLiveData<Integer> categoryId = new MutableLiveData<>();
    private final MutableLiveData<List<CartModel>> _cartItems = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<CartModel>> getCartItems() {
        return _cartItems;
    }

    @Inject
    public ShareViewModel() {
    }

    public void setCategoryId(int id) {
        categoryId.setValue(id);
    }



    public void addItem(CartModel cartModel) {
        List<CartModel> currentList = _cartItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        CartModel existingItem = null;
        for (CartModel item : currentList) {
            if (item.getProduct().getId() == cartModel.getProduct().getId()) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            currentList.add(cartModel);
        }

        _cartItems.setValue(currentList);
    }

    public void removeItem(CartModel cartModel) {
        List<CartModel> currentList = _cartItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        CartModel existingItem = null;
        for (CartModel item : currentList) {
            if (item.getId() == cartModel.getId()) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            if (existingItem.getQuantity() > 1) {
                existingItem.setQuantity(existingItem.getQuantity() - 1);
            } else {
                currentList.remove(existingItem);
            }
        }

        _cartItems.setValue(currentList);
    }
    public LiveData<Integer> getCategoryId() {
        return categoryId;
    }
    public void updateCartItems(List<CartModel> newCartItems) {
        _cartItems.setValue(newCartItems);
    }
}