package com.milanix.shutter.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityHomeBinding;
import com.milanix.shutter.feed.favorite.FavoriteListFragment;
import com.milanix.shutter.feed.list.FeedListFragment;
import com.milanix.shutter.notification.list.NotificationListFragment;
import com.milanix.shutter.post.NewPostActivity;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.milanix.shutter.user.profile.ProfileModule;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import static com.milanix.shutter.home.HomeActivity.Tab.FAVORITES;
import static com.milanix.shutter.home.HomeActivity.Tab.FEEDS;
import static com.milanix.shutter.home.HomeActivity.Tab.NOTIFICATIONS;

/**
 * Activity containing home view
 *
 * @author milan
 */
public class HomeActivity extends AbstractBindingActivity<ActivityHomeBinding> implements HomeContract.View, OnTabSelectListener {

    @StringDef({FEEDS, FAVORITES, NOTIFICATIONS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Tab {
        String FEEDS = "FEEDS";
        String FAVORITES = "FAVORITES";
        String NOTIFICATIONS = "NOTIFICATIONS";
    }

    @Inject
    protected HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_home);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getUserComponent().with(new HomeModule(this)).inject(this);
        presenter.subscribe();

        switchToDefaultTab(savedInstanceState, getIntent().getAction());
    }

    @Override
    protected void performBinding(@LayoutRes int layout) {
        super.performBinding(layout);
        binding.setTabSelectListener(this);
        binding.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    private void switchToDefaultTab(Bundle savedInstanceState, String action) {
        if (null == savedInstanceState) {
            if (!TextUtils.isEmpty(action))
                switchFragment(action);
            else
                switchFragment(Tab.FEEDS);
        }
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_feeds:
                binding.fabAdd.show();
                switchFragment(Tab.FEEDS);
                break;
            case R.id.tab_favorites:
                binding.fabAdd.hide();
                switchFragment(FAVORITES);
                break;
            case R.id.tab_notifications:
                binding.fabAdd.hide();
                switchFragment(Tab.NOTIFICATIONS);
                break;
        }
    }

    @Override
    public void setUser(FirebaseUser user) {
        binding.setUser(user);
    }

    @Override
    public void openProfile(String profileId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.ivAvatar,
                getString(R.string.transition_profile_image));

        startActivity(new Intent(this, ProfileActivity.class).putExtra(ProfileModule.PROFILE_ID, profileId),
                options.toBundle());
    }

    @Override
    public void addPost() {
        startActivity(new Intent(this, NewPostActivity.class));
        overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
    }

    @Override
    public void removeUnnotified() {
        binding.bottomBar.getTabWithId(R.id.tab_notifications).removeBadge();
    }

    @Override
    public void showUnnotified(int unnotifiedCount) {
        binding.bottomBar.getTabWithId(R.id.tab_notifications).setBadgeCount(unnotifiedCount < 100 ? unnotifiedCount : 99);
    }

    private void switchFragment(@Tab String tab) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (tab) {
            case Tab.FEEDS:
                transaction.replace(R.id.container, new FeedListFragment(), tab);
                break;
            case FAVORITES:
                transaction.replace(R.id.container, new FavoriteListFragment(), tab);
                break;
            case Tab.NOTIFICATIONS:
                transaction.replace(R.id.container, new NotificationListFragment(), tab);
                break;
        }

        transaction.commitNow();
    }
}
