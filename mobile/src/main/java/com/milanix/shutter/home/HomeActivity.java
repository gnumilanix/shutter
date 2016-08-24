package com.milanix.shutter.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityHomeBinding;
import com.milanix.shutter.feed.favorite.view.FavoriteListFragment;
import com.milanix.shutter.feed.list.view.FeedListFragment;
import com.milanix.shutter.notification.view.NotificationListFragment;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Activity containing feeds
 *
 * @author milan
 */
public class HomeActivity extends AbstractBindingActivity<ActivityHomeBinding> implements OnTabSelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_home);
        binding.setTabSelectListener(this);

        if (null == savedInstanceState) {
            switchFragment(Tab.FEEDS);
        }
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_feeds:
                switchFragment(Tab.FEEDS);
                break;
            case R.id.tab_favorites:
                switchFragment(Tab.FAVORITES);
                break;
            case R.id.tab_notifications:
                switchFragment(Tab.NOTIFICATIONS);
                break;
        }
    }

    private void switchFragment(@Tab String tab) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (tab) {
            case Tab.FEEDS:
                transaction.replace(R.id.container, new FeedListFragment(), tab);
                break;
            case Tab.FAVORITES:
                transaction.replace(R.id.container, new FavoriteListFragment(), tab);
                break;
            case Tab.NOTIFICATIONS:
                transaction.replace(R.id.container, new NotificationListFragment(), tab);
                break;
        }

        transaction.commitNow();
    }
}
