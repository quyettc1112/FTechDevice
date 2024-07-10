package com.example.ftechdevice.Common.CommonAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.Model.OrderDetailModel;
import com.example.ftechdevice.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    private List<OrderDetailModel> orderDetailList;
    private Context context;

    public OrderDetailAdapter(Context context, List<OrderDetailModel> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetailModel orderDetail = orderDetailList.get(position);
        holder.tvProductName.setText(orderDetail.getProduct().getName());
        holder.tvProductQuantity.setText("Số lượng: " + orderDetail.getQuantity());
        holder.tvProductPrice.setText("Giá: " + orderDetail.getPrice() + " VND");

        Glide.with(context).load(orderDetail.getProduct().getImageUrl()).into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return orderDetailList != null ? orderDetailList.size() : 0;
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName;
        TextView tvProductQuantity;
        TextView tvProductPrice;
        ImageView ivProductImage;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
        }
    }
}
