package com.milanix.shutter.core;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.App;
import com.milanix.shutter.R;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

/**
 * Abstract implementation of activities to be used in this application. This will provide {@link IPresenter} injection and {@link ViewDataBinding}
 *
 * @author milan
 */
public abstract class AbstractFragment<P extends IPresenter, B extends ViewDataBinding> extends Fragment implements IView {
    @Inject
    protected P presenter;
    protected B binding;

    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitions();
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        overridePendingTransitions();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransitions();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransitions();
    }

    private void overridePendingTransitions() {
        getActivity().overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
    }

    @Override
    public boolean isActive() {
        return isAdded();
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
