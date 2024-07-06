package com.example.ftechdevice.API_Service;

import com.example.ftechdevice.Model.CartModule.CartDTO;
import com.example.ftechdevice.Model.CartModule.CartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartAPI_Service {

    @POST("api/cart")
    Call<CartResponse> addToCart(
            @Header("Authorization") String token,
            @Body CartDTO cartDTO
            );

    @GET("api/cart")
    Call<List<CartResponse>> getCarts(
            @Header("Authorization") String token,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("search") String search
    );

    @PUT("api/cart/{id}")
    Call<CartResponse> updateCart(
            @Header("Authorization") String token,
            @Path("id") int id
    );

}
