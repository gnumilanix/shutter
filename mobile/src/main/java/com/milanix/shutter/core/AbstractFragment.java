package com.milanix.shutter.core;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.App;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

/**
 * Abstract implementation of activities to be used in this application. This will provide {@link IPresenter} injection and {@link ViewDataBinding}
 *
 * @author milan
 */
public abstract class AbstractFragment<T extends IPresenter, B extends ViewDataBinding> extends Fragment {
    @Inject
    protected T presenter;
    protected B binding;

    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public App getApp() {
        return ((App) getActivity().getApplication());
    }

    public AppComponent getAppComponent() {
        return getApp().getAppComponent();
    }

    public UserComponent getUserComponent() {
        return getApp().getUserComponent();
    }
}
