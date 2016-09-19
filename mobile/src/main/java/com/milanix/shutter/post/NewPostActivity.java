package com.milanix.shutter.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityNewPostBinding;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

/**
 * Activity containing a new post related view components
 *
 * @author milan
 */
public class NewPostActivity extends AbstractBindingActivity<ActivityNewPostBinding> {
    public static final String TAG_FRAGMENT_NEW_POST = "_fragment_new_post";

    @Inject
    protected LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_new_post);
        setToolbar(binding.toolbar, true);
        handleIntent(getUserComponent(), getIntent(), savedInstanceState);
    }

    private void handleIntent(UserComponent userComponent, Intent intent, Bundle savedInstanceState) {
        if (null == userComponent) {
            handleNonAuthorizedUser();
        } else {
            getUserComponent().inject(this);
            processIntent(intent, savedInstanceState);
        }
    }

    private void processIntent(Intent intent, Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            final NewPostFragment newPostFragment = new NewPostFragment();
            newPostFragment.setArguments(intent.getExtras());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, newPostFragment,
                    TAG_FRAGMENT_NEW_POST).commitNow();
        } else {
            localBroadcastManager.sendBroadcast(intent);
        }
    }

    private void handleNonAuthorizedUser() {
        finish();
        startActivity(new Intent(this, LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right);
    }
}
