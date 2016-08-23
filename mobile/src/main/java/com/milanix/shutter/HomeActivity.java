package com.milanix.shutter;

import android.os.Bundle;
import android.support.annotation.IdRes;

import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityHomeBinding;
import com.milanix.shutter.feed.list.view.FeedListFragment;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Activity containing feeds
 *
 * @author milan
 */
public class HomeActivity extends AbstractBindingActivity<ActivityHomeBinding> implements OnTabSelectListener {
    public static final String TAG_FRAGMENT_FEEDS = "_fragment_feeds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_home);
        binding.setTabSelectListener(this);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FeedListFragment(),
                    TAG_FRAGMENT_FEEDS).commit();
        }
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {

    }
}
