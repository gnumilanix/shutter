package com.milanix.shutter.post;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityNewPostBinding;

/**
 * Activity containing a single feed
 *
 * @author milan
 */
public class NewPostActivity extends AbstractBindingActivity<ActivityNewPostBinding> {
    public static final String TAG_FRAGMENT_FEEDS = "_fragment_feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_new_post);
        setToolbar();

//        if (null == savedInstanceState) {
//            final FeedDetailFragment feedFragment = new FeedDetailFragment();
//            feedFragment.setArguments(getIntent().getExtras());
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, feedFragment,
//                    TAG_FRAGMENT_FEEDS).commit();
//        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
