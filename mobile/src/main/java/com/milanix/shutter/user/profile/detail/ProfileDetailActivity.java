package com.milanix.shutter.user.profile.detail;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityProfileBinding;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.ProfileModule;

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class ProfileDetailActivity extends AbstractBindingActivity<ActivityProfileBinding> implements ProfileDetailFragment.OnReadyListener {
    public static final String TAG_FRAGMENT_PROFILE = "_fragment_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_profile);
        setToolbar();

        if (null == savedInstanceState) {
            getApp().createProfileComponent(getIntent().getExtras().getString(ProfileModule.PROFILE_ID));
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileDetailFragment(),
                    TAG_FRAGMENT_PROFILE).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onReady(ProfileDetailContract.View view, Profile profile) {
        binding.setProfile(profile);
    }
}
