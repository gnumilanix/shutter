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
public class NewPostActivity extends AbstractBindingActivity<ActivityNewPostBinding> implements NewPostFragment.OnReadyListener {
    public static final String TAG_FRAGMENT_NEW_POST = "_fragment_new_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_new_post);
        setToolbar();

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewPostFragment(),
                    TAG_FRAGMENT_NEW_POST).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onReady(NewPostContract.View view, NewPost post) {
    }
}
