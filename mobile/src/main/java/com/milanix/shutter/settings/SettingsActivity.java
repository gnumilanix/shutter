package com.milanix.shutter.settings;

import android.os.Bundle;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityPostDetailBinding;
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
        setToolbar();

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment(),
                    TAG_FRAGMENT_SETTINGS).commit();
        }
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
