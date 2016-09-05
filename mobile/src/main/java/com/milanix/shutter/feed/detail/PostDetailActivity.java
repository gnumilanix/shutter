package com.milanix.shutter.feed.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityPostDetailBinding;
import com.milanix.shutter.feed.PostModule;

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
            getApp().createPostComponent(getIntent().getExtras().getString(PostModule.POST_ID));
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new PostDetailFragment(),
                    TAG_FRAGMENT_POST_DETAIL).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
