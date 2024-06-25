package com.example.ftechdevice.UI.Fragment.CartFragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseAdapter;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseItemViewHolderCF;
import com.example.ftechdevice.Model.CartModel;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.databinding.ItemToyCartBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class CartAdapter extends BaseAdapter<CartModel, CartAdapter.CartAdapterViewHolder> {
    private Function<CartModel, Void> onAddQuantityItemClickListener;
    private Function<CartModel, Void> onRemoveQuantityItemClickListener;

    public void setOnAddQuantityItemClickListener(Function<CartModel, Void> onAddQuantityItemClickListener) {
        this.onAddQuantityItemClickListener = onAddQuantityItemClickListener;
    }

    public void setOnRemoveQuantityItemClickListener(Function<CartModel, Void> onRemoveQuantityItemClickListener) {
        this.onRemoveQuantityItemClickListener = onRemoveQuantityItemClickListener;
    }

    public class CartAdapterViewHolder extends BaseItemViewHolderCF<CartModel, ItemToyCartBinding> {
        public CartAdapterViewHolder(ItemToyCartBinding binding) {
            super(binding);
        }

        @Override
        public void bind(CartModel item) {
            binding.imItemCartImage.setImageResource(item.getToyModel().getToyImage());
            binding.tvItemCartProductName.setText(item.getToyModel().getToyName());
            binding.tvItemCartProductPrice.setText(formatPrice(item.getToyModel().getToyPrice()) + " VND");
            binding.tvItemCartProductCategory.setText(item.getToyModel().getCategoryModel().getName());
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

    public void addItem(ToyModel toyModel) {
        List<CartModel> currentList = getCurrentList();
        CartModel existingItem = currentList.stream()
                .filter(item -> item.getToyModel().getId() == toyModel.getId())
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            currentList.add(CartModel.create(toyModel, 1));
        }
        submitList(currentList);
    }

    public void removeItem(ToyModel toyModel) {
        List<CartModel> currentList = getCurrentList();
        CartModel existingItem = currentList.stream()
                .filter(item -> item.getToyModel().getId() == toyModel.getId())
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            if (existingItem.getQuantity() > 1) {
                existingItem.setQuantity(existingItem.getQuantity() - 1);
            } else {
                currentList.remove(existingItem);
            }
        }
        submitList(currentList);
    }

    public int getTotalItems() {
        return getCurrentList().stream().mapToInt(CartModel::getQuantity).sum();
    }

    public double getTotalItemsPrice() {
        return getCurrentList().stream().mapToDouble(item -> item.getToyModel().getToyPrice() * item.getQuantity()).sum();
    }

    public List<CartModel> getCurrentList() {
        return differ.getCurrentList();
    }

    public void updateCartItems(List<CartModel> newCartItems) {
        submitList(newCartItems);
        notifyDataSetChanged();
    }

    @Override
    public DiffUtil.ItemCallback<CartModel> differCallBack() {
        return new DiffUtil.ItemCallback<CartModel>() {
            @Override
            public boolean areItemsTheSame(CartModel oldItem, CartModel newItem) {
                return oldItem.getId() == newItem.getId();
            }
            @Override
            public boolean areContentsTheSame(CartModel oldItem, CartModel newItem) {
                return oldItem.getId() == newItem.getId();
            }
        };
    }

    @Override
    public CartAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemToyCartBinding binding = ItemToyCartBinding.inflate(inflater, parent, false);
        return new CartAdapterViewHolder(binding);
    }
}
