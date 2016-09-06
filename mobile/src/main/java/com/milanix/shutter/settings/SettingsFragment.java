package com.milanix.shutter.settings;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.milanix.shutter.R;

//todo comment
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, PermissionListener {

    private MultiplePermissionsListener dialogStoragePermissionListener;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        findPreference(getString(R.string.pref_muzei)).setOnPreferenceClickListener(this);
        createPermissionListeners();

        Dexter.continuePendingRequestsIfPossible(dialogStoragePermissionListener);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_muzei))) {
            return validatePermissions();
        }

        return false;
    }

    private boolean validatePermissions() {
        if (!isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) || !isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (Dexter.isRequestOngoing()) {
                Dexter.continuePendingRequestsIfPossible(dialogStoragePermissionListener);
            } else {
                Dexter.checkPermissions(dialogStoragePermissionListener, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            return true;
        }

        return false;
    }

    private boolean isGranted(String permission) {
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void createPermissionListeners() {
        dialogStoragePermissionListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder.withContext(getActivity())
                        .withTitle(R.string.storage_permission_request_title)
                        .withMessage(R.string.storage_permission_request_rationale_muzei)
                        .withButtonText(android.R.string.ok)
                        .build();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {

    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        showPermissionRationale(token);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.storage_permission_request_title)
                .setMessage(R.string.storage_permission_request_rationale_muzei)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }
}
