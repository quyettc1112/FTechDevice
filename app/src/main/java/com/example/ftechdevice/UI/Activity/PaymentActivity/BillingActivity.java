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

import com.example.ftechdevice.API_Repository.CartAPI_Repository;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.API_Repository.OrderAPI_Repository;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.CartModule.CartModel;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.Model.ModelRespone.OrderResponse;
import com.example.ftechdevice.Model.OrderDetailModel;
import com.example.ftechdevice.Model.PostOrder;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.OrderListActivity.OrderListActivity;
import com.example.ftechdevice.databinding.ActivityBillingBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
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

    @Inject
    CartAPI_Repository cartAPIRepository;

    @Inject
    OrderAPI_Repository orderAPIRepository;

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
                    if (getUserFromJWT() != null) {
                        callDeleteAllCartByUserId(getUserFromJWT().getUserId());
                    }

                    //call api post new order
                    postNewOrder();

                }
                Intent intent = new Intent(BillingActivity.this, OrderListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void postNewOrder() {
        Log.d("BillingActivity", "Inside postNewOrder()");
        List<OrderDetailModel> orderDetails = new ArrayList<>();
        for (CartModel cart : listCartModel) {
            Log.d("BillingActivity", "Processing cart item: " + cart.getProduct().getName());

            ProductModel product = new ProductModel();
            product.setId(cart.getProduct().getId());

            OrderDetailModel orderDetail = new OrderDetailModel(
                    cart.getQuantity(),
                    String.valueOf(cart.getProduct().getPrice()),
                    product,
                    1
            );
            orderDetails.add(orderDetail);
            Log.d("BillingActivity", "OrderDetailModel added: " + orderDetail.toString());
        }

        UserJWT userJWT = getUserFromJWT();
        if (userJWT == null) {
            Log.d("BillingActivity", "Failed to get user from JWT");
            return;
        }

        int userId = userJWT.getUserId();
        String token = userJWT.getAccessToken();

        PostOrder order = new PostOrder(
                "Order Title",
                "Order Description",
                userId,
                1,
                orderDetails
        );
        Log.d("BillingActivity", "PostOrder created: " + order.toString());

        Gson gson = new Gson();
        String orderJson = gson.toJson(order);
        Log.d("BillingActivity", "Order JSON: " + orderJson);

        if (token != null && !token.isEmpty()) {
            orderAPIRepository.createOrder("Bearer " + token, order).enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    Log.d("BillingActivity", "API call onResponse");
                    if (response.isSuccessful()) {
                        Log.d("BillingActivity", "Order posted successfully");
                    } else {
                        Log.d("BillingActivity", "Failed to post order: " + response.code());
                        try {
                            Log.d("BillingActivity", "Response error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    Log.d("BillingActivity", "Error order: " + t.getMessage());
                }
            });
        } else {
            Log.d("BillingActivity", "Token is null or empty");
        }
    }

    private void callDeleteAllCartByUserId(int userId) {
        cartAPIRepository.deleteAllCartByUserId("Bearer "+getUserFromJWT().getAccessToken(), userId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("callDeleteAllCartByUserId", "Delete All Cart Successful");
                        } else  Log.d("callDeleteAllCartByUserId", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("callDeleteAllCartByUserId", t.getMessage());
                    }
                });

    }

    private UserJWT getUserFromJWT() {
        String accessToken = TokenManager.getAccessToken(BillingActivity.this);
        if (accessToken != null) {
            try {
                JSONObject decodedPayload = JWTDecoder.decodeJWT(accessToken);

                UserJWT user = new UserJWT();
                user.setAccessToken(accessToken);
                user.setSubject(decodedPayload.getString("sub"));
                user.setEmail(decodedPayload.getString("email"));
                user.setUserId(decodedPayload.getInt("userId"));
                user.setRoleName(decodedPayload.getString("RoleName"));
                user.setPhone(decodedPayload.getString("phone"));
                user.setIssuedAt(decodedPayload.getLong("iat"));
                user.setExpiration(decodedPayload.getLong("exp"));

                return user;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
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