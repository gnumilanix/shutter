package com.milanix.shutter.auth.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivitySignupBinding;

//// TODO: 30/8/2016 add comment
public class SignUpActivity extends AbstractBindingActivity<ActivitySignupBinding> implements SignUpFragment.OnReadyListener {
    public static final String TAG_FRAGMENT_SIGNUP = "_fragment_signup";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_signup);
        setToolbar();

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignUpFragment(),
                    TAG_FRAGMENT_SIGNUP).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onReady(SignUpContract.View view, SignUp signUp) {
        binding.setView(view);
        binding.setSignup(signUp);
    }
}
