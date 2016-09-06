package com.milanix.shutter.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.milanix.shutter.R;

//todo comment
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
