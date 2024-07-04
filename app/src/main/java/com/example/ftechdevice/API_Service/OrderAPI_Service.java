package com.example.ftechdevice.API_Service;

import com.example.ftechdevice.Model.ModelRespone.OrderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderAPI_Service {
    @GET("/api/orders")
    Call<OrderResponse> getAllOrders(@Query("user_id") int user_id);
}
