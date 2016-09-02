package com.milanix.shutter.feed.detail;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityPostDetailBinding;

/**
 * Activity containing a single feed
 *
 * @author milan
 */
public class PostDetailActivity extends AbstractBindingActivity<ActivityPostDetailBinding> {
    public static final String TAG_FRAGMENT_POST_DETAIL = "_fragment_post_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_post_detail);
        setToolbar();

        if (null == savedInstanceState) {
            final PostDetailFragment postDetailFragment = new PostDetailFragment();
            postDetailFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, postDetailFragment,
                    TAG_FRAGMENT_POST_DETAIL).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
