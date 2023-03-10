package com.milanix.shutter.user.profile;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityProfileBinding;

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class ProfileActivity extends AbstractBindingActivity<ActivityProfileBinding> {
    public static final String TAG_FRAGMENT_PROFILE = "_fragment_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_profile);
        setToolbar();

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment(),
                    TAG_FRAGMENT_PROFILE).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
