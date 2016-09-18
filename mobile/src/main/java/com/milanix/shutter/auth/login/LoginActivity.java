package com.milanix.shutter.auth.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityLoginBinding;

/**
 * Activity containing login related view
 *
 * @author milan
 */
public class LoginActivity extends AbstractBindingActivity<ActivityLoginBinding> {
    public static final String TAG_FRAGMENT_LOGIN = "_fragment_login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_login);
        setToolbar(binding.toolbar, true);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(),
                    TAG_FRAGMENT_LOGIN).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right);
    }
}
