package com.example.ftechdevice.UI.Activity.OrderListActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.API_Repository.OrderAPI_Repository;
import com.example.ftechdevice.Common.CommonAdapter.OrderAdapter;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.ModelRespone.OrderResponse;
import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.ShareViewModel.UserShareViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class OrderListActivity extends AppCompatActivity {

    private RecyclerView rvOrderList;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderList;
    private ImageView ivBack;
    @Inject
    OrderAPI_Repository orderAPIRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        rvOrderList = findViewById(R.id.rv_order_list);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getUserFromJWT() != null) {
            String token = getUserFromJWT().getAccessToken();
            Log.d("OrderListActivity", "Token: " + token);
            callOrderAPI(token);
        }

    }

    private void callOrderAPI(String token) {
        Log.d("OrderListActivity", "Calling API with token: " + token);
        orderAPIRepository.getAllOrder("Bearer " + token,0,10).enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                Log.d("OrderListActivity", "API call response received");
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderModel> orders = response.body();
                    if (orders != null) {
                        Log.d("OrderListActivity", "Orders size: " + orders.size());
                        orderList = orders;
                        orderAdapter = new OrderAdapter(OrderListActivity.this, orderList);
                        rvOrderList.setAdapter(orderAdapter);
                    } else {
                        Log.d("OrderListActivity", "No orders found");
                    }
                } else {
                    Log.d("OrderListActivity", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                Log.d("OrderListActivity", t.getMessage());
            }
        });
    }


    private UserJWT getUserFromJWT() {
        String accessToken = TokenManager.getAccessToken(OrderListActivity.this);
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
}
