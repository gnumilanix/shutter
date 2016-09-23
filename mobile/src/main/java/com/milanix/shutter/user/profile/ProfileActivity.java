package com.milanix.shutter.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
import static com.milanix.shutter.user.profile.ProfileActivity.Tab.FOLLOWERS;
import static com.milanix.shutter.user.profile.ProfileActivity.Tab.FOLLOWINGS;
import static com.milanix.shutter.user.profile.ProfileActivity.Tab.POSTS;
import static com.milanix.shutter.user.profile.ProfileModule.PROFILE_TAB;

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class ProfileActivity extends AbstractBindingActivity<ActivityProfileBinding> implements ProfileContract.View,
        IComponentProvider<ProfileComponent> {
    @IntDef({POSTS, FOLLOWERS, FOLLOWINGS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Tab {
        int FOLLOWERS = 0;
        int POSTS = 1;
        int FOLLOWINGS = 2;
    }

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            binding.tvFollowersHint.setTypeface(null, position == FOLLOWERS ? BOLD : NORMAL);
            binding.tvPostsHint.setTypeface(null, position == POSTS ? BOLD : NORMAL);
            binding.tvFollowingHint.setTypeface(null, position == FOLLOWINGS ? BOLD : NORMAL);

            binding.hlFollowers.setVisibility(position == FOLLOWERS ? View.VISIBLE : View.GONE);
            binding.hlPosts.setVisibility(position == POSTS ? View.VISIBLE : View.GONE);
            binding.hlFollowings.setVisibility(position == FOLLOWINGS ? View.VISIBLE : View.GONE);
        }
    };
    private ProfileComponent profileComponent;

    @Inject
    protected ProfileContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        performBinding(R.layout.activity_profile);
        setToolbar(binding.toolbar, true);
        setUpPager();
        switchToDefaultTab(savedInstanceState, getIntent().getIntExtra(PROFILE_TAB, POSTS));
    }

    private void setUpPager() {
        binding.vpContainer.setAdapter(new PageAdapter(this, getSupportFragmentManager(), getPages()));
        binding.vpContainer.addOnPageChangeListener(pageChangeListener);
    }

    private List<PageAdapter.Page> getPages() {
        final List<PageAdapter.Page> page = new ArrayList<>();
        page.add(FOLLOWERS, new PageAdapter.Page(FollowerListFragment.class, null));
        page.add(POSTS, new PageAdapter.Page(PostListFragment.class, null));
        page.add(FOLLOWINGS, new PageAdapter.Page(FollowingListFragment.class, null));

        return page;
    }

    private void switchToDefaultTab(Bundle savedInstanceState, int action) {
        if (null == savedInstanceState) {
            switchFragment(Tab.POSTS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
        binding.vpContainer.removeOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected void performBinding(@LayoutRes int layout) {
        super.performBinding(layout);
        binding.setView(this);
        binding.setPresenter(presenter);
        presenter.subscribe();
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

    private void switchFragment(@ProfileActivity.Tab int tab) {
        binding.vpContainer.setCurrentItem(tab);
    }

    private void launchSettings() {
        startActivity(new Intent(this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
    }
}
