package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.Model.OrderModel;
import com.example.ftechdevice.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderModel> orderList;

    public OrderAdapter(List<OrderModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.tvOrderTitle.setText(order.getTitle());
        holder.tvOrderStatus.setText("Trạng thái: " + (order.getStatus() == 1 ? "Hoàn thành" : "Chưa hoàn thành"));
        holder.tvOrderDescription.setText(order.getDescription());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderTitle;
        TextView tvOrderStatus;
        TextView tvOrderDescription;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTitle = itemView.findViewById(R.id.tv_order_title);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            tvOrderDescription = itemView.findViewById(R.id.tv_order_description);
        }
    }
}
