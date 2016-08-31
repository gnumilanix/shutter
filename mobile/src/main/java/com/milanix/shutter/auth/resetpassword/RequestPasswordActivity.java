package com.milanix.shutter.auth.resetpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityRequestPasswordBinding;

//// TODO: 30/8/2016 add comment
public class RequestPasswordActivity extends AbstractBindingActivity<ActivityRequestPasswordBinding> {
    public static final String TAG_FRAGMENT_RESET_PASSWORD = "_fragment_reset_password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_request_password);
        setToolbar();

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new RequestPasswordFragment(),
                    TAG_FRAGMENT_RESET_PASSWORD).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
