package com.milanix.shutter.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.ActivityProfileBinding;
import com.milanix.shutter.settings.SettingsActivity;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.followers.FollowerListFragment;
import com.milanix.shutter.user.profile.followings.FollowingListFragment;
import com.milanix.shutter.user.profile.posts.PostListFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
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
        getComponent().inject(this);
        performBinding(R.layout.activity_profile);
        setToolbar(binding.toolbar, true);
        presenter.subscribe();

        switchToDefaultTab(savedInstanceState, getIntent().getAction());
    }

    private void switchToDefaultTab(Bundle savedInstanceState, String action) {
        if (null == savedInstanceState) {
            if (!TextUtils.isEmpty(action))
                switchFragment(action);
            else
                switchFragment(Tab.POSTS);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
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
            case R.id.action_settings:
                launchSettings();
                return true;
            case R.id.action_logout:
                presenter.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (null != presenter && presenter.isMe()) {
            final MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_profile, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public ProfileComponent getComponent() {
        if (null == profileComponent)
            profileComponent = getUserComponent().with(new ProfileModule(getIntent().getExtras().getString(ProfileModule.PROFILE_ID), this));

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
        updateSelection(tab);

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(tab);

        if (null == fragment) {
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (tab) {
                case FOLLOWERS:
                    transaction.replace(R.id.container, new FollowerListFragment(), tab);
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

    private void updateSelection(@ProfileActivity.Tab String tab) {
        binding.tvFollowers.setTypeface(null, tab.equals(FOLLOWERS) ? BOLD : NORMAL);
        binding.tvFollowersHint.setTypeface(null, tab.equals(FOLLOWERS) ? BOLD : NORMAL);
        binding.tvPosts.setTypeface(null, tab.equals(POSTS) ? BOLD : NORMAL);
        binding.tvPostsHint.setTypeface(null, tab.equals(POSTS) ? BOLD : NORMAL);
        binding.tvFollowing.setTypeface(null, tab.equals(FOLLOWINGS) ? BOLD : NORMAL);
        binding.tvFollowingHint.setTypeface(null, tab.equals(FOLLOWINGS) ? BOLD : NORMAL);
    }

    private void launchSettings() {
        startActivity(new Intent(this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
    }
}
