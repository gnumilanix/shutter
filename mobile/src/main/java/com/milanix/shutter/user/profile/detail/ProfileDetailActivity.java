package com.milanix.shutter.user.profile.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityProfileBinding;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.ProfileComponent;
import com.milanix.shutter.user.profile.ProfileModule;

// TODO: 5/9/2016 refactor profile detail fragment to only contain posts and define view and presenter contract for this activity instead.

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class ProfileDetailActivity extends AbstractBindingActivity<ActivityProfileBinding> implements ProfileDetailFragment.OnReadyListener {
    public static final String TAG_FRAGMENT_PROFILE = "_fragment_profile";
    private ProfileComponent profileComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_profile);
        setToolbar();
        createProfileComponent(getIntent().getExtras().getString(ProfileModule.PROFILE_ID));

        if (null == savedInstanceState) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReady(ProfileDetailContract.View view, ProfileDetailContract.Presenter presenter, Profile profile) {
        binding.setView(view);
        binding.setPresenter(presenter);
        binding.setProfile(profile);
    }

    @Override
    public ProfileComponent getProfileComponent() {
        return profileComponent;
    }

    public void createProfileComponent(String profileId) {
        profileComponent = getUserComponent().with(new ProfileModule(profileId));
    }
}
