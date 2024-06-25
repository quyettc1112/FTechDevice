package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.VNPay_Service;
import com.example.ftechdevice.Model.ModelRespone.UrlResponseDTO;
import com.example.ftechdevice.Model.ModelRespone.VNPayResponse;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import retrofit2.Call;

@ActivityScoped
public class VNPay_Repository {
    private final VNPay_Service vnPayService;

    @Inject
    public VNPay_Repository(VNPay_Service vnPayService) {
        this.vnPayService = vnPayService;
    }


    public Call<UrlResponseDTO> submitOrder(int amount, String orderInfo) {
        return vnPayService.submitOrder(amount, orderInfo);
    }

    public Call<VNPayResponse> getPaymentResult(String url){
        return vnPayService.getPaymentResult(url);
    }
}