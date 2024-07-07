package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
    private List<ProductModel> products;
    private OnItemClickListener onItemClickListener;
    private OnItemCartClickListener onItemCartClickListener;


    public ProductListAdapter(List<ProductModel> products, OnItemClickListener onItemClickListener, OnItemCartClickListener onItemCartClickListener) {
        this.products = products;
        this.onItemClickListener = onItemClickListener;
        this.onItemCartClickListener = onItemCartClickListener;
    }

    public ProductListAdapter() {
    }

    public ProductListAdapter(List<ProductModel> products) {
        this.products = products;
    }

    public void setProductList(List<ProductModel> products) {
        this.products = products;
    }

    public interface OnItemClickListener {
        void onItemClick(ProductModel product);
    }

    public interface OnItemCartClickListener {
        void onItemCartClick(ProductModel product);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemCartClickListener(OnItemCartClickListener onItemCartClickListener) {
        this.onItemCartClickListener = onItemCartClickListener;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_toy_list, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        ProductModel product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {

        private final ImageView toyImageView;
        private final TextView tvToyPrice;
        private final TextView tvToyName;
        private final LinearLayout laAddToCarts;

        public ProductListViewHolder(@NonNull View view) {
            super(view);
            toyImageView = view.findViewById(R.id.im_toy_image);
            tvToyPrice = view.findViewById(R.id.tv_toy_price);
            tvToyName = view.findViewById(R.id.tv_toy_name);
            laAddToCarts = view.findViewById(R.id.la_addToCarts);
        }

        public void bind(ProductModel p) {
            Glide.with(toyImageView.getContext())
                    .load(p.getImageUrl())
                    .into(toyImageView);
            tvToyPrice.setText(formatPrice(p.getPrice()) + " VND");
            tvToyName.setText(p.getName());
            laAddToCarts.setOnClickListener(v -> {
                if (onItemCartClickListener != null) {
                    onItemCartClickListener.onItemCartClick(p);
                }
            });

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(p);
                }
            });
        }

        private String formatPrice(double price) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(price);
        }
    }
}
