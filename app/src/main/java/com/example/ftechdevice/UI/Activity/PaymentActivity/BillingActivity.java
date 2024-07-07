package com.example.ftechdevice.UI.Activity.PaymentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ftechdevice.Model.CartModule.CartModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.OrderListActivity.OrderListActivity;
import com.example.ftechdevice.databinding.ActivityBillingBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BillingActivity extends AppCompatActivity {
    String orderInfo;
    ArrayList<CartModel> listCartModel;
    double amount;
    int transactionId;
    int orderId;
    int totalPrice;
    String paymentTime;
    String status;

    private ActivityBillingBinding billingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billingBinding = ActivityBillingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(billingBinding.getRoot());
        getIntentExtraValue();
        if (Integer.parseInt(status.trim()) == 1){
            billingBinding.ivStatus.setImageResource(R.drawable.paymentsuccess);
        }else billingBinding.ivStatus.setImageResource(R.drawable.paymentfail);
        int amountInt = (int)Math.round(amount);
        billingBinding.tvAmountValue.setText(String.valueOf(amountInt + " VND"));
        billingBinding.tvTransferFee.setText("20.000 VND");
        billingBinding.tvTransferId.setText(String.valueOf(transactionId));
        billingBinding.tvYouTransfer.setText(String.valueOf(totalPrice));
        billingBinding.tvStatus.setText(String.valueOf(status));
        billingBinding.tvDate.setText(String.valueOf(LocalDateTime.now()));
        billingBinding.tvTimeDetail.setText(String.valueOf(paymentTime));
        billingBinding.tvTotalAmount.setText(String.valueOf(amountInt) + " VND");
        billingBinding.tvYouTransfer.setText("Thanh toan gio hang");
        //vao day de intent qua order

        //click to home icon -> intent value to
        billingBinding.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(status.trim()) == 1){
                    //clear cart call api
                    //call api post new order
                }
                Intent intent = new Intent(BillingActivity.this, OrderListActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getIntentExtraValue() {
        // Lấy intent
        Intent intent = getIntent();
        // Lấy các giá trị từ intent
        amount = intent.getDoubleExtra("amount", 0.0);
        status = intent.getStringExtra("status");
        transactionId = intent.getIntExtra("transactionId", 0);
        orderId = intent.getIntExtra("orderId", orderId);
        totalPrice = intent.getIntExtra("totalPrice", 0);
        paymentTime = intent.getStringExtra("paymentTime");
        listCartModel = intent.getParcelableArrayListExtra("list_cart_model");

        // In ra dữ liệu
        Log.d("PaymentActivity", String.format("Amount: %.2f", amount));
        Log.d("PaymentActivity", "Order Info: " + orderInfo);
        if (listCartModel != null) {
            for (CartModel item : listCartModel) {
                Log.d("PaymentActivity", "Cart ID: " + item.getId());
                Log.d("PaymentActivity", "Product ID: " + item.getProduct().getId());
                Log.d("PaymentActivity", "Product name: " + item.getProduct().getName());
                Log.d("PaymentActivity", "Product Price: " + item.getProduct().getPrice());
                Log.d("PaymentActivity", "Quantity : " + item.getQuantity());
                Log.d("PaymentActivity", "---------------- ");
            }
        } else {
            Log.d("PaymentActivity", "Cart Item list is null");
        }

    }
}