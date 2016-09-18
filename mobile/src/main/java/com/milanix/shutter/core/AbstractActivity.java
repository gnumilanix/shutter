package com.milanix.shutter.core;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.milanix.shutter.App;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.user.UserComponent;

/**
 * Abstract implementation of activities to be used in this application.
 *
 * @author milan
 */
public abstract class AbstractActivity extends AppCompatActivity implements IView {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setToolbar(Toolbar toolbar, boolean homeAsUp) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
        getSupportActionBar().setDisplayShowHomeEnabled(homeAsUp);
    }
}
