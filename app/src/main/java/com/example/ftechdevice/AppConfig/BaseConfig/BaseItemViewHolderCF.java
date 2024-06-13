package com.example.ftechdevice.AppConfig.BaseConfig;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseItemViewHolderCF<T, VB extends ViewBinding> extends RecyclerView.ViewHolder {

    protected final VB binding;
    protected final Context itemContext;

    public BaseItemViewHolderCF(VB binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.itemContext = binding.getRoot().getContext();
    }

    public abstract void bind(T item);
}
