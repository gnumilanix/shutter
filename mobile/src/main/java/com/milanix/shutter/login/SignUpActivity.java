package com.milanix.shutter.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityAuthBinding;
import com.milanix.shutter.databinding.ActivitySignupBinding;

//// TODO: 30/8/2016 add comment
public class SignUpActivity extends AbstractBindingActivity<ActivitySignupBinding> {
    public static final String TAG_FRAGMENT_LOGIN = "_fragment_login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_signup);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignUpFragment(),
                    TAG_FRAGMENT_LOGIN).commit();
        }
    }
}
