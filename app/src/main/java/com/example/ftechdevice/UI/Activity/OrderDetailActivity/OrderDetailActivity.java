package com.example.ftechdevice.UI.Activity;

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
import com.example.ftechdevice.Model.OrderDetailModel;
import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.R;

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
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzMTMydHJpbmciLCJlbWFpbCI6ImFkbWluM0BnbWFpbC5jb20iLCJ1c2VySWQiOjEsIlJvbGVOYW1lIjoiQURNSU4iLCJwaG9uZSI6InN0cmluZyIsImlhdCI6MTcyMDI2NTUyMywiZXhwIjoxNzIwMzUxOTIzfQ.0BIj_wPtzFvCUl4O5t4WrIILs4CLcvg4ijAOwvIzfNk";
            getOrderDetails(token, orderId);
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                    orderDetailAdapter = new OrderDetailAdapter(OrderDetailActivity.this, orderDetailList);
                    rvOrderDetails.setAdapter(orderDetailAdapter);
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
