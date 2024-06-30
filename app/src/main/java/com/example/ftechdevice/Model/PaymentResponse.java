package com.example.ftechdevice.Model;

public class PaymentResponse {
    private String code;
    private String data;
    private String orderId;
    private String totalPrice;
    private String paymentTime;
    private String transactionId;

    // Getters và setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getTotalPrice() { return totalPrice; }
    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }

    public String getPaymentTime() { return paymentTime; }
    public void setPaymentTime(String paymentTime) { this.paymentTime = paymentTime; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}
