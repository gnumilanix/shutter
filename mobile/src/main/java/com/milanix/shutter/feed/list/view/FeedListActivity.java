package com.milanix.shutter.feed.list.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.milanix.shutter.R;

/**
 * Activity containing feeds
 *
 * @author milan
 */
public class FeedListActivity extends AppCompatActivity {
    public static final String TAG_FRAGMENT_FEEDS = "_fragment_feeds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multipane);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FeedListFragment(),
                    TAG_FRAGMENT_FEEDS).commit();
        }
    }
}
