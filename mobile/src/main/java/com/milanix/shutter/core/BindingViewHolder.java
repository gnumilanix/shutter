package com.milanix.shutter.core;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * View holder for wrapping {@link ViewDataBinding}
 *
 * @author milan
 */
public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T binding;

    public BindingViewHolder(T binding) {
        super(binding.getRoot());

        this.binding = binding;
    }
}
