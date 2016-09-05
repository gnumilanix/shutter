package com.milanix.shutter.user.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.ActivityProfileBinding;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.posts.PostListFragment;

import javax.inject.Inject;

// TODO: 5/9/2016 refactor profile detail fragment to only contain posts and define view and presenter contract for this activity instead.

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class ProfileActivity extends AbstractBindingActivity<ActivityProfileBinding> implements ProfileContract.View,
        IComponentProvider<ProfileComponent> {
    public static final String TAG_FRAGMENT_PROFILE = "_fragment_profile";
    private ProfileComponent profileComponent;
    private ProgressDialog progressDialog;

    @Inject
    protected ProfileContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_profile);
        setToolbar();
        createProfileComponent(getIntent().getExtras().getString(ProfileModule.PROFILE_ID));

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new PostListFragment(),
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
            case R.id.action_logout:
                presenter.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_logout).setVisible(null != presenter && presenter.isMe());

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public ProfileComponent getComponent() {
        return profileComponent;
    }

    public void createProfileComponent(String profileId) {
        profileComponent = getUserComponent().with(new ProfileModule(profileId, this));
    }

    @Override
    public void setProfile(Profile profile) {
        binding.setView(this);
        binding.setPresenter(presenter);
        binding.setProfile(profile);

        invalidateOptionsMenu();
    }

    @Override
    public void handleProfileRefreshError() {
        Snackbar.make(binding.root, R.string.error_refresh_profile, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void handleToggleFollowError() {

    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, getString(R.string.title_uploading_post), getString(R.string.message_uploading_post), true);
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    @Override
    public void logoutComplete() {
        presenter.toggleFollow();
    }

    @Override
    public void toggleFollow() {
        finish();
        startActivity(new Intent(this, LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
