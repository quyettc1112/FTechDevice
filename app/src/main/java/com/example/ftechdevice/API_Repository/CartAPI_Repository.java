package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.CartAPI_Service;
import com.example.ftechdevice.API_Service.OrderAPI_Service;
import com.example.ftechdevice.Model.CartModule.CartDTO;
import com.example.ftechdevice.Model.CartModule.CartResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import retrofit2.Call;

@ActivityScoped
public class CartAPI_Repository{
    private final CartAPI_Service cartAPIService;

    @Inject
    public CartAPI_Repository(CartAPI_Service cartAPIService) {
        this.cartAPIService = cartAPIService;
    }

    public Call<CartResponse> addToCart(String token, CartDTO cartDTO) {
        return cartAPIService.addToCart(token, cartDTO);
    }
    public Call<List<CartResponse>> getCarts(String token, int pageNo, int pageSize, String search) {
        return cartAPIService.getCarts(token,pageNo, pageSize, search);
    }


}
