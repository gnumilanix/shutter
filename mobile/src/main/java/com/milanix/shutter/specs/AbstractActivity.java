package com.milanix.shutter.specs;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.milanix.shutter.App;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

/**
 * Abstract implementation of activities to be used in this application. This will provide {@link IPresenter} injection and {@link ViewDataBinding}
 *
 * @author milan
 */
public abstract class AbstractActivity<T extends IPresenter, B extends ViewDataBinding> extends AppCompatActivity {
    @Inject
    protected T presenter;
    protected B binding;

    protected void performBinding(@LayoutRes int layout) {
        binding = DataBindingUtil.setContentView(this, layout);
    }

    public App getApp() {
        return ((App) getApplication());
    }

    public AppComponent getAppComponent() {
        return getApp().getAppComponent();
    }

    public UserComponent getUserComponent() {
        return getApp().getUserComponent();
    }
}
