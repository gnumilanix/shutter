package com.milanix.shutter.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.ActivityProfileBinding;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.followings.FollowingListFragment;
import com.milanix.shutter.user.profile.posts.PostListFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import static com.milanix.shutter.user.profile.ProfileActivity.Tab.FOLLOWERS;
import static com.milanix.shutter.user.profile.ProfileActivity.Tab.FOLLOWINGS;
import static com.milanix.shutter.user.profile.ProfileActivity.Tab.POSTS;

// TODO: 5/9/2016 refactor profile detail fragment to only contain posts and define view and presenter contract for this activity instead.

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class ProfileActivity extends AbstractBindingActivity<ActivityProfileBinding> implements ProfileContract.View,
        IComponentProvider<ProfileComponent> {
    @StringDef({POSTS, FOLLOWERS, FOLLOWINGS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Tab {
        String POSTS = "POSTS";
        String FOLLOWERS = "FOLLOWERS";
        String FOLLOWINGS = "FOLLOWINGS";
    }

    private ProfileComponent profileComponent;

    @Inject
    protected ProfileContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createProfileComponent(getIntent().getExtras().getString(ProfileModule.PROFILE_ID)).inject(this);
        performBinding(R.layout.activity_profile);
        setToolbar();
        presenter.subscribe();

        if (null == savedInstanceState) {
            viewPosts();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void performBinding(@LayoutRes int layout) {
        super.performBinding(layout);
        binding.setView(this);
        binding.setPresenter(presenter);
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

    public ProfileComponent createProfileComponent(String profileId) {
        profileComponent = getUserComponent().with(new ProfileModule(profileId, this));

        return profileComponent;
    }

    @Override
    public void viewPosts() {
        switchFragment(Tab.POSTS);
    }

    @Override
    public void viewFollowings() {
        switchFragment(Tab.FOLLOWINGS);
    }

    @Override
    public void viewFollowers() {
        switchFragment(Tab.FOLLOWERS);
    }

    @Override
    public void setProfile(Profile profile) {
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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void logoutComplete() {
        finish();
        startActivity(new Intent(this, LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private void switchFragment(@ProfileActivity.Tab String tab) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (tab) {
            case FOLLOWERS:
                transaction.replace(R.id.container, new FollowingListFragment(), tab);
                break;
            case FOLLOWINGS:
                transaction.replace(R.id.container, new FollowingListFragment(), tab);
                break;
            case POSTS:
                transaction.replace(R.id.container, new PostListFragment(), tab);
                break;
        }

        transaction.commitNow();
    }
}