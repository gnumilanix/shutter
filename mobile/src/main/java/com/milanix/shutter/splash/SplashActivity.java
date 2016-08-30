package com.milanix.shutter.splash;

import android.content.Intent;
import android.os.Bundle;

import com.milanix.shutter.core.AbstractActivity;
import com.milanix.shutter.home.HomeActivity;
import com.milanix.shutter.login.AuthActivity;

import javax.inject.Inject;

/**
 * Splash activity
 *
 * @author milan
 */
public class SplashActivity extends AbstractActivity implements SplashContract.View{
    @Inject
    protected SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().with(new SplashModule(this)).inject(this);
        presenter.subscribe();
    }

    @Override
    public void setSessionAvailable() {
        startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    public void setSessionUnavailable() {
        startActivity(new Intent(this, AuthActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
