package com.milanix.shutter.feed.detail.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.milanix.shutter.R;

/**
 * Activity containing a single feed
 *
 * @author milan
 */
public class FeedDetailActivity extends AppCompatActivity {
    public static final String TAG_FRAGMENT_FEEDS = "_fragment_feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepane);

        if (null == savedInstanceState) {
            final FeedDetailFragment feedFragment = new FeedDetailFragment();
            feedFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, feedFragment,
                    TAG_FRAGMENT_FEEDS).commit();
        }
    }
}
