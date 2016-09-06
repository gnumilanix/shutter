package com.milanix.shutter.settings;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivitySettingsBinding;

/**
 * Activity containing a single feed
 *
 * @author milan
 */
public class SettingsActivity extends AbstractBindingActivity<ActivitySettingsBinding> {
    public static final String TAG_FRAGMENT_SETTINGS = "_fragment_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_settings);
        setToolbar(binding.toolbar, true);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment(),
                    TAG_FRAGMENT_SETTINGS).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right);
    }

}