package com.milanix.shutter.core;

import android.databinding.ViewDataBinding;

import javax.inject.Inject;

/**
 * Abstract implementation of activities to be used in this application. This will provide  {@link ViewDataBinding}
 *
 * @author milan
 */
public abstract class AbstractActivity<T extends IPresenter, B extends ViewDataBinding> extends AbstractBindingActivity<B> {
    @Inject
    protected T presenter;
}
