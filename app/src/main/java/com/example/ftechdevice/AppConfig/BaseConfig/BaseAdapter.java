package com.example.ftechdevice.AppConfig.BaseConfig;

import android.view.View;

import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseItemViewHolderCF<T, ?>> extends RecyclerView.Adapter<VH> {

    private OnItemClickListener<T> onItemClickListener;
    private SetItemOrderByListener<T> setItemOrderBy;

    protected abstract DiffUtil.ItemCallback<T> differCallBack();

    protected final AsyncListDiffer<T> differ = new AsyncListDiffer<>(this, differCallBack());

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = differ.getCurrentList().get(position);
        holder.bind(item);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }

    public void submitList(List<T> list) {
        if (setItemOrderBy != null && list != null) {
            setItemOrderBy.setItemOrderBy(list);
        }
        differ.submitList(list);
        notifyDataSetChanged();
    }

    public void setItemOrderBy(SetItemOrderByListener<T> listener) {
        setItemOrderBy = listener;
    }

    public void setItemOnClickListener(OnItemClickListener<T> listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }

    public interface SetItemOrderByListener<T> {
        void setItemOrderBy(List<T> list);
    }
}
