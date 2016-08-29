package com.milanix.shutter.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractActivity;
import com.milanix.shutter.databinding.ActivityHomeBinding;
import com.milanix.shutter.feed.favorite.FavoriteListFragment;
import com.milanix.shutter.feed.list.FeedListFragment;
import com.milanix.shutter.notification.NotificationListFragment;
import com.milanix.shutter.user.model.User;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import static com.milanix.shutter.home.HomeActivity.Tab.FAVORITES;
import static com.milanix.shutter.home.HomeActivity.Tab.FEEDS;
import static com.milanix.shutter.home.HomeActivity.Tab.NOTIFICATIONS;

/**
 * Activity containing feeds
 *
 * @author milan
 */
public class HomeActivity extends AbstractActivity<HomeContract.Presenter, ActivityHomeBinding> implements HomeContract.View, OnTabSelectListener {

    @StringDef({FEEDS, FAVORITES, NOTIFICATIONS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Tab {
        String FEEDS = "FEEDS";
        String FAVORITES = "FAVORITES";
        String NOTIFICATIONS = "NOTIFICATIONS";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_home);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getUserComponent().with(new HomeModule(this)).inject(this);
        binding.setTabSelectListener(this);
        binding.setView(this);
        presenter.getUser();

        if (null == savedInstanceState) {
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
    public void setUser(User user) {
        binding.setUser(user);
    }

    @Override
    public void openProfile(User user) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void addPost() {
        Snackbar.make(binding.root, "New post not yet implemented", Snackbar.LENGTH_SHORT).show();
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
