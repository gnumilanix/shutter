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
    public static final String TAG_FRAGMENT_NEW_POST = "_fragment_new_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_new_post);
        setToolbar(binding.toolbar, true);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewPostFragment(),
                    TAG_FRAGMENT_NEW_POST).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right);
    }
}
