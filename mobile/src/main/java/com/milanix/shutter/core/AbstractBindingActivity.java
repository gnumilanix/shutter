package com.milanix.shutter.core;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

/**
 * Abstract implementation of activities to be used in this application. This will provide  {@link ViewDataBinding}
 *
 * @author milan
 */
public abstract class AbstractBindingActivity<B extends ViewDataBinding> extends AbstractActivity {
    protected B binding;

    protected void performBinding(@LayoutRes int layout) {
        binding = DataBindingUtil.setContentView(this, layout);
    }
}
