package com.example.ftechdevice.Model.ModelRespone;

import com.google.gson.annotations.SerializedName;

public class VNPayResponse {
    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private String data;

    @SerializedName("orderId")
    private String orderId;

    @SerializedName("totalPrice")
    private String totalPrice;

    @SerializedName("paymentTime")
    private String paymentTime;

    @SerializedName("transactionId")
    private String transactionId;

    public String getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
