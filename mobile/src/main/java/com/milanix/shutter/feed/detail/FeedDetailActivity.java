package com.milanix.shutter.feed.detail;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityFeedDetailBinding;

/**
 * Activity containing a single feed
 *
 * @author milan
 */
public class FeedDetailActivity extends AbstractBindingActivity<ActivityFeedDetailBinding> {
    public static final String TAG_FRAGMENT_FEEDS = "_fragment_feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_feed_detail);
        setToolbar();

        if (null == savedInstanceState) {
            final FeedDetailFragment feedFragment = new FeedDetailFragment();
            feedFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, feedFragment,
                    TAG_FRAGMENT_FEEDS).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
