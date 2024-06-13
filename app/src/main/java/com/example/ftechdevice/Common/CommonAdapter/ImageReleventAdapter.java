package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseAdapter;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseItemViewHolderCF;
import com.example.ftechdevice.Model.ImageReleventModel;
import com.example.ftechdevice.databinding.ItemImageRelveventBinding;

import java.util.function.Consumer;

public class ImageReleventAdapter extends BaseAdapter<ImageReleventModel, ImageReleventAdapter.ItemReleventViewHolder> {

    private Consumer<ImageReleventModel> onItemImageClickListener;

    public void setOnItemImageClickListener(Consumer<ImageReleventModel> listener) {
        this.onItemImageClickListener = listener;
    }

    @NonNull
    @Override
    public ItemReleventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemImageRelveventBinding binding = ItemImageRelveventBinding.inflate(inflater, parent, false);
        return new ItemReleventViewHolder(binding);
    }

    @Override
    public DiffUtil.ItemCallback<ImageReleventModel> differCallBack() {
        return new DiffUtil.ItemCallback<ImageReleventModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ImageReleventModel oldItem, @NonNull ImageReleventModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ImageReleventModel oldItem, @NonNull ImageReleventModel newItem) {
                return oldItem.getId() == newItem.getId();
            }
        };
    }

    public class ItemReleventViewHolder extends BaseItemViewHolderCF<ImageReleventModel, ItemImageRelveventBinding> {

        public ItemReleventViewHolder(ItemImageRelveventBinding binding) {
            super(binding);
        }

        @Override
        public void bind(ImageReleventModel item) {
            binding.imToyImage.setImageResource(item.getImage());
            binding.imToyImage.setOnClickListener(v -> {
                if (onItemImageClickListener != null) {
                    onItemImageClickListener.accept(item);
                }
            });
        }
    }
}
