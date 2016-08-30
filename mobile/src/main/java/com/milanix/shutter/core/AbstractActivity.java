package com.milanix.shutter.core;

import android.support.v7.app.AppCompatActivity;

import com.milanix.shutter.App;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.user.UserComponent;

/**
 * Abstract implementation of activities to be used in this application.
 *
 * @author milan
 */
public class AbstractActivity extends AppCompatActivity implements IView {
    private boolean isDestroyed = false;

    public App getApp() {
        return ((App) getApplication());
    }

    public AppComponent getAppComponent() {
        return getApp().getAppComponent();
    }

    public UserComponent getUserComponent() {
        return getApp().getUserComponent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isDestroyed = true;
    }

    @Override
    public boolean isActive() {
        return !isDestroyed;
    }
}
