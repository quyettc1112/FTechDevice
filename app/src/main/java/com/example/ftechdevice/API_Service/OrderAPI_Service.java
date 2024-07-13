package com.example.ftechdevice.API_Service;

import com.example.ftechdevice.Model.ModelRespone.OrderResponse;
import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.Model.PostOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderAPI_Service {
    @GET("/api/orders")
    Call<List<OrderModel>>  getAllOrders(
            @Header("Authorization") String token,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize);

    @GET("/api/orders/{id}")
    Call<OrderModel> getOrderDetails(
            @Header("Authorization") String token,
            @Path("id") int orderId);

    @POST("/api/orders")
    Call<OrderResponse> createOrder(
            @Header("Authorization") String authToken,
            @Body PostOrder order);
}
