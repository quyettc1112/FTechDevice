package com.example.ftechdevice.UI.Activity.OrderDetailActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.API_Repository.OrderAPI_Repository;
import com.example.ftechdevice.Common.CommonAdapter.OrderDetailAdapter;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.OrderDetailModel;
import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.OrderListActivity.OrderListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class OrderDetailActivity extends AppCompatActivity {

    private RecyclerView rvOrderDetails;
    private OrderDetailAdapter orderDetailAdapter;
    private List<OrderDetailModel> orderDetailList;
    private TextView tvOrderTitle, tvOrderDescription, tvOrderStatus;
    private int orderId;
    private ImageView ivBack;

    @Inject
    OrderAPI_Repository orderAPIRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        rvOrderDetails = findViewById(R.id.rv_order_details);
        rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));

        tvOrderTitle = findViewById(R.id.tv_order_title);
        tvOrderDescription = findViewById(R.id.tv_order_description);
        tvOrderStatus = findViewById(R.id.tv_order_status);
        ivBack = findViewById(R.id.iv_back);

        orderId = getIntent().getIntExtra("orderId", -1);

        if (orderId != -1) {
            if (getUserFromJWT() != null) {
                String token = getUserFromJWT().getAccessToken();
                getOrderDetails(token, orderId);
            }
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private UserJWT getUserFromJWT() {
        String accessToken = TokenManager.getAccessToken(OrderDetailActivity.this);
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

    private void getOrderDetails(String token, int orderId) {
        orderAPIRepository.getOrderDetails("Bearer " + token, orderId).enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderModel order = response.body();
                    tvOrderTitle.setText(order.getTitle());
                    tvOrderDescription.setText(order.getDescription());
                    tvOrderStatus.setText(order.getStatus() == 1 ? "Hoàn thành" : "Chưa hoàn thành");

                    orderDetailList = order.getOrderDetailList();
                    if (orderDetailList != null && !orderDetailList.isEmpty()) {
                        orderDetailAdapter = new OrderDetailAdapter(OrderDetailActivity.this, orderDetailList);
                        rvOrderDetails.setAdapter(orderDetailAdapter);
                    } else {
                        Log.d("OrderDetailActivity", "No order details found");
                    }
                } else {
                    Log.d("OrderDetailActivity", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                Log.d("OrderDetailActivity", t.getMessage());
            }
        });

    }
}
