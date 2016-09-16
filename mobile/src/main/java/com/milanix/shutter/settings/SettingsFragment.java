package com.milanix.shutter.settings;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.milanix.shutter.App;
import com.milanix.shutter.R;

import javax.inject.Inject;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

//todo comment
@RuntimePermissions
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, SettingsContract.View {
    private CheckBoxPreference muzeiPref;

    @Inject
    protected SettingsContract.Presenter presenter;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        ((App) getActivity().getApplication()).getAppComponent().with(new SettingsModule(this)).inject(this);

        muzeiPref = (CheckBoxPreference) findPreference(getString(R.string.pref_muzei));
        muzeiPref.setOnPreferenceClickListener(this);
        presenter.validateMuzeiSettings();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_muzei))) {
            presenter.validateMuzeiSettings();
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SettingsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void requestMuzeiInstallation() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_install_muzei)
                .setMessage(R.string.message_muzei_install)
                .setPositiveButton(getText(R.string.action_install_now), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.handleMuzeiNotInstalled();
                    }
                })
                .show();
    }

    @Override
    public void installMuzei(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void cannotEnableMuzei() {
        muzeiPref.setChecked(false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void validateStoragePermission() {
        muzeiPref.setChecked(true);
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

    @Override
    public void requestMuzeiStoragePermission() {
        SettingsFragmentPermissionsDispatcher.validateStoragePermissionWithCheck(this);
    }
}
