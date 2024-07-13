package com.example.ftechdevice.API_Repository;

import android.util.Log;

import com.example.ftechdevice.API_Service.OrderAPI_Service;
import com.example.ftechdevice.Model.ModelRespone.OrderResponse;
import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.Model.PostOrder;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import retrofit2.Call;

@ActivityScoped
public class OrderAPI_Repository {
    private final OrderAPI_Service orderAPIService;

    @Inject
    public OrderAPI_Repository(OrderAPI_Service orderAPIService) {
        this.orderAPIService = orderAPIService;
    }

    public Call<List<OrderModel>>  getAllOrder(String token, int pageIndex, int pageSize) {
        return orderAPIService.getAllOrders(token,pageIndex,pageSize);
    }

    public Call<OrderModel> getOrderDetails(String token,int orderId) {
        return orderAPIService.getOrderDetails(token, orderId);
    }
    public Call<OrderResponse> createOrder(String authToken, PostOrder order) {
        return orderAPIService.createOrder(authToken,order);
    }
}

