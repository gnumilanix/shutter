package com.milanix.shutter.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityAuthBinding;

/**
 * Created by milan on 30/8/2016.
 */
public class AuthActivity extends AbstractBindingActivity<ActivityAuthBinding> {
    public static final String TAG_FRAGMENT_AUTH = "_fragment_auth";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_auth);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AuthFragment(),
                    TAG_FRAGMENT_AUTH).commit();
        }
    }
}
