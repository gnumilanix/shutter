package com.milanix.shutter.web;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityWebpageBinding;

/**
 * Activity containing a web page
 *
 * @author milan
 */
public class WebPageActivity extends AbstractBindingActivity<ActivityWebpageBinding> {
    public static final String TAG_FRAGMENT_WEBPAGE = "_fragment_webpage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_webpage);
        setToolbar(binding.toolbar, true);

        if (null == savedInstanceState) {
            final WebPageFragment webPageFragment = new WebPageFragment();
            webPageFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, webPageFragment,
                    TAG_FRAGMENT_WEBPAGE).commit();
        }
    }

    @Override
    protected void performBinding(@LayoutRes int layout) {
        super.performBinding(layout);
        binding.setActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right);
    }

    public void completeReading() {
        onBackPressed();
    }
}
