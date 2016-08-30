package com.milanix.shutter.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityAuthBinding;

//// TODO: 30/8/2016 add comment
public class LoginActivity extends AbstractBindingActivity<ActivityAuthBinding> {
    public static final String TAG_FRAGMENT_LOGIN = "_fragment_login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_auth);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(),
                    TAG_FRAGMENT_LOGIN).commit();
        }
    }
}
