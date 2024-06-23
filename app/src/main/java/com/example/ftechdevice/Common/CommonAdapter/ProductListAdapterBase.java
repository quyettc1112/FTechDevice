package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseAdapter;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseItemViewHolderCF;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.databinding.RecycleItemToyListBinding;

import java.text.DecimalFormat;

public class ProductListAdapterBase extends BaseAdapter<ProductModel, ProductListAdapterBase.ProductBaseItemViewHolder>  {

    private OnItemCartClickListener onItemCartClickListener;

    public interface OnItemCartClickListener {
        void onItemCartClick(ProductModel productModel);
    }

    public void setOnItemCartClickListener(OnItemCartClickListener listener) {
        this.onItemCartClickListener = listener;
    }

    public class ProductBaseItemViewHolder extends BaseItemViewHolderCF<ProductModel, RecycleItemToyListBinding> {
        public ProductBaseItemViewHolder(@NonNull RecycleItemToyListBinding binding) {
            super(binding);
        }

        @Override
        public void bind(ProductModel item) {
            Glide.with(binding.imToyImage.getContext())
                    .load(item.getImageUrl())
                    .into(binding.imToyImage);
            binding.tvToyPrice.setText(formatPrice(item.getPrice()) + " VND");
            binding.tvToyName.setText(item.getName());
            binding.laAddToCarts.setOnClickListener(v -> {
                if (onItemCartClickListener != null) {
                    onItemCartClickListener.onItemCartClick(item);
                }
            });
        }

        private String formatPrice(double price) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(price);
        }
    }

    @Override
    public DiffUtil.ItemCallback<ProductModel> differCallBack() {
        return new DiffUtil.ItemCallback<ProductModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ProductModel oldItem, @NonNull ProductModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ProductModel oldItem, @NonNull ProductModel newItem) {
                return oldItem.getId() == newItem.getId();
            }
        };
    }

    @NonNull
    @Override
    public ProductBaseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecycleItemToyListBinding binding = RecycleItemToyListBinding.inflate(inflater, parent, false);
        return new ProductBaseItemViewHolder(binding);
    }
}
