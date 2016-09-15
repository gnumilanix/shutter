package com.milanix.shutter.settings;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.milanix.shutter.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;
import permissions.dispatcher.RuntimePermissions;

//todo comment
@RuntimePermissions
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {


    private CheckBoxPreference muzeiPref;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);

        muzeiPref = (CheckBoxPreference) findPreference(getString(R.string.pref_muzei));
        muzeiPref.setOnPreferenceClickListener(this);
        validateMuzeiSettings();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_muzei))) {
            validateMuzeiSettings();
            SettingsFragmentPermissionsDispatcher.validateStoragePermissionWithCheck(this);
        }

        return false;
    }

    private void validateMuzeiSettings() {
        if (!PermissionUtils.hasSelfPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
            muzeiPref.setChecked(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SettingsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void validateStoragePermission() {
    }

    @OnPermissionDenied(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @OnNeverAskAgain(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onPermissionDenied() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.storage_permission_request_title)
                .setMessage(R.string.storage_permission_request_rationale_avatar)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @OnShowRationale(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void showPermissionRationale(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.storage_permission_request_title)
                .setMessage(R.string.storage_permission_request_rationale_muzei)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        request.cancel();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        request.proceed();
                    }
                }).show();
    }
}
