package com.example.ftechdevice.UI.Activity.OrderListActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.API_Repository.OrderAPI_Repository;
import com.example.ftechdevice.Common.CommonAdapter.OrderAdapter;
import com.example.ftechdevice.Model.ModelRespone.OrderResponse;
import com.example.ftechdevice.Model.ModelRespone.UserResponseDTO;
import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.ShareViewModel.UserShareViewModel;

import java.util.ArrayList;
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
    private UserShareViewModel userShareViewModel;

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

        userShareViewModel = new ViewModelProvider(this).get(UserShareViewModel.class);
        observeViewModel();
    }
        private void observeViewModel() {
            userShareViewModel.getUserResponse().observe(this, new Observer<UserResponseDTO>() {
                @Override
                public void onChanged(UserResponseDTO userResponseDTO) {
                    if (userResponseDTO != null) {
                        int userId = userResponseDTO.getId();
                        callOrderAPI(userId);
                    } else {
                    }
                }
            });
        }

    private void callOrderAPI(int userId) {
        orderAPIRepository.getAllOrder(userId).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderModel> orders = response.body().getContent();
                    if (orders != null) {
                        orderList = orders;
                        orderAdapter = new OrderAdapter(orderList);
                        rvOrderList.setAdapter(orderAdapter);
                    }
                } else {
                    Log.d("OrderListActivity", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("OrderListActivity", t.getMessage());
            }
        });
    }
}
