package com.example.ftechdevice.UI.Fragment.CartFragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseAdapter;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseItemViewHolderCF;
import com.example.ftechdevice.Model.CartModel;
import com.example.ftechdevice.Model.CartModule.CartResponse;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.databinding.ItemToyCartBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class CartAdapter_FTech extends BaseAdapter<CartResponse, CartAdapter_FTech.CartAdapterFtechViewHolder> {

    private Function<CartResponse, Void> onAddQuantityItemClickListener;
    private Function<CartResponse, Void> onRemoveQuantityItemClickListener;

    public void setOnAddQuantityItemClickListener(Function<CartResponse, Void> onAddQuantityItemClickListener) {
        this.onAddQuantityItemClickListener = onAddQuantityItemClickListener;
    }

    public void setOnRemoveQuantityItemClickListener(Function<CartResponse, Void> onRemoveQuantityItemClickListener) {
        this.onRemoveQuantityItemClickListener = onRemoveQuantityItemClickListener;
    }

    @Override
    protected DiffUtil.ItemCallback<CartResponse> differCallBack() {
        return new DiffUtil.ItemCallback<CartResponse>() {
            @Override
            public boolean areItemsTheSame(CartResponse oldItem, CartResponse newItem) {
                return oldItem.getId() == newItem.getId();
            }
            @Override
            public boolean areContentsTheSame(CartResponse oldItem, CartResponse newItem) {
                return oldItem.getId() == newItem.getId();
            }
        };
    }

    @NonNull
    @Override
    public CartAdapterFtechViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemToyCartBinding binding = ItemToyCartBinding.inflate(inflater, parent, false);
        return new CartAdapterFtechViewHolder(binding);
    }


    public class CartAdapterFtechViewHolder extends BaseItemViewHolderCF<CartResponse, ItemToyCartBinding> {
        public CartAdapterFtechViewHolder(ItemToyCartBinding binding) {
            super(binding);
        }
        @Override
        public void bind(CartResponse item) {

            CartResponse.Product product = item.getProduct();
            if (product == null) {
                return;
            }

            if (item.getProduct().getImageUrl().toString().isEmpty()) {
                binding.imItemCartImage.setImageResource(R.color.background_profile);
            } else {
                Picasso.get()
                        .load(item.getProduct().getImageUrl()) // Assuming item.img is the URL string
                    .placeholder(R.color.background_profile) // Optional: Placeholder image while loading
                        .error(R.color.background_profile) // Optional: Error image to display on load failure
                        .into(binding.imItemCartImage);
            }

            binding.tvItemCartProductName.setText(product.getName());
            binding.tvItemCartProductPrice.setText(formatPrice(product.getPrice()) + " VND");
            binding.tvItemCartProductCategory.setText(product.getProductCategory().getName());
            binding.tvItemCartProductQuantity.setText(String.valueOf(item.getQuantity()));


            binding.imPlusQuantity.setOnClickListener(v -> {
                if (onAddQuantityItemClickListener != null) {
                    onAddQuantityItemClickListener.apply(item);
                }
                binding.tvItemCartProductQuantity.setText(String.valueOf(item.getQuantity()));
            });

            binding.imMinusQuantity.setOnClickListener(v -> {
                if (onRemoveQuantityItemClickListener != null) {
                    onRemoveQuantityItemClickListener.apply(item);
                }
                binding.tvItemCartProductQuantity.setText(String.valueOf(item.getQuantity()));
            });
        }

        public String formatPrice(double price) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(price);
        }


    }

    public List<CartResponse> getCurrentList() {
        return differ.getCurrentList();
    }


}
