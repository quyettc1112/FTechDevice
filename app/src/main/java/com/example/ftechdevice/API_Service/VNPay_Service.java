package com.example.ftechdevice.API_Service;

import com.example.ftechdevice.Model.ModelRespone.UrlResponseDTO;
import com.example.ftechdevice.Model.ModelRespone.VNPayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface VNPay_Service {
    @POST("/vnpay/submitOrder")
    Call<UrlResponseDTO> submitOrder(@Query("amount") int amount, @Query("orderInfo") String orderInfo);




    @GET
    Call<VNPayResponse> getPaymentResult(@Url String url);
}
