package com.milanix.shutter.auth.resetpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityRequestPasswordBinding;

/**
 * Activity containing reset password view
 *
 * @author milan
 */
public class RequestPasswordActivity extends AbstractBindingActivity<ActivityRequestPasswordBinding> {
    public static final String TAG_FRAGMENT_RESET_PASSWORD = "_fragment_reset_password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_request_password);
        setToolbar(binding.toolbar, true);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new RequestPasswordFragment(),
                    TAG_FRAGMENT_RESET_PASSWORD).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right);
    }
}
