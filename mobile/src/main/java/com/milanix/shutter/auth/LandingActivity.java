package com.milanix.shutter.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityLandingBinding;

/**
 * Created by milan on 30/8/2016.
 */
public class LandingActivity extends AbstractBindingActivity<ActivityLandingBinding> {
    public static final String TAG_FRAGMENT_AUTH = "_fragment_auth";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_landing);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LandingFragment(),
                    TAG_FRAGMENT_AUTH).commit();
        }
    }
}
