package com.milanix.shutter.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractActivity;
import com.milanix.shutter.home.HomeActivity;

import javax.inject.Inject;

/**
 * Splash activity
 *
 * @author milan
 */
public class SplashActivity extends AbstractActivity implements SplashContract.View {
    @Inject
    protected SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().with(new SplashModule(this)).inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setSessionAvailable() {
        startActivity(HomeActivity.class);
    }

    @Override
    public void setSessionUnavailable() {
        startActivity(LandingActivity.class);
    }

    private <T extends Activity> void startActivity(Class<T> clazz) {
        finish();
        startActivity(new Intent(this, clazz).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
        overridePendingTransition(0, R.anim.activity_fade_out);
    }
}
