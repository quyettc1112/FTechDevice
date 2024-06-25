package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseAdapter;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseItemViewHolderCF;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.databinding.RecycleItemToyListBinding;

import java.text.DecimalFormat;

public class ToyListAdapterBase extends BaseAdapter<ToyModel, ToyListAdapterBase.ToyBaseItemViewHolder>  {

    private OnItemCartClickListener onItemCartClickListener;

    public interface OnItemCartClickListener {
        void onItemCartClick(ToyModel toyModel);
    }

    public void setOnItemCartClickListener(OnItemCartClickListener listener) {
        this.onItemCartClickListener = listener;
    }

    public class ToyBaseItemViewHolder extends BaseItemViewHolderCF<ToyModel, RecycleItemToyListBinding> {
        public ToyBaseItemViewHolder(@NonNull RecycleItemToyListBinding binding) {
            super(binding);
        }

        @Override
        public void bind(ToyModel item) {
            binding.imToyImage.setImageResource(item.getToyImage());
            binding.tvToyPrice.setText(formatPrice(item.getToyPrice()) + " VND");
            binding.tvToyName.setText(item.getToyName());
            binding.tvStarRating.setText("(" + item.getToyRating() + ")");
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
    public DiffUtil.ItemCallback<ToyModel> differCallBack() {
        return new DiffUtil.ItemCallback<ToyModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ToyModel oldItem, @NonNull ToyModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ToyModel oldItem, @NonNull ToyModel newItem) {
                return oldItem.getId() == newItem.getId();
            }
        };
    }

    @NonNull
    @Override
    public ToyBaseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecycleItemToyListBinding binding = RecycleItemToyListBinding.inflate(inflater, parent, false);
        return new ToyBaseItemViewHolder(binding);
    }
}
