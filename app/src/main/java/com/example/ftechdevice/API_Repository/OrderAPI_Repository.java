package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.OrderAPI_Service;
import com.example.ftechdevice.Model.ModelRespone.OrderResponse;

import javax.inject.Inject;
import retrofit2.Call;

public class OrderAPI_Repository {
    private final OrderAPI_Service orderAPIService;

    @Inject
    public OrderAPI_Repository(OrderAPI_Service orderAPIService) {
        this.orderAPIService = orderAPIService;
    }

    public Call<OrderResponse> getAllOrder(int user_id) {
        return orderAPIService.getAllOrders(user_id);
    }
}
