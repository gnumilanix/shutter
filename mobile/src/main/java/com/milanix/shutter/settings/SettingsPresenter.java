package com.milanix.shutter.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

import permissions.dispatcher.PermissionUtils;

/**
 * New post presenter
 *
 * @author milan
 */
public class SettingsPresenter extends AbstractPresenter<SettingsContract.View> implements SettingsContract.Presenter {
    private static final String PKG_MUZEI = "net.nurik.roman.muzei";
    private static final String URI_MARKET_MUZEI = "market://details?id=" + PKG_MUZEI;
    private static final String URI_PLAY_MUZEI = "https://play.google.com/store/apps/details?id=" + PKG_MUZEI;
    private Context context;
    private PackageManager packageManager;

    @Inject
    public SettingsPresenter(SettingsContract.View view, Context context, PackageManager packageManager) {
        super(view);
        this.context = context;
        this.packageManager = packageManager;
    }

    @Override
    public void validateMuzeiSettings() {
        if (isMuzeiInstalled()) {
            checkStoragePermission();
        } else if (isActive()) {
            view.requestMuzeiInstallation();
        }
    }

    @Override
    public void handleMuzeiNotInstalled() {
        if (isActive()) {
            try {
                view.installMuzei(new Intent(Intent.ACTION_VIEW, Uri.parse(URI_MARKET_MUZEI)));
            } catch (android.content.ActivityNotFoundException anfe) {
                view.installMuzei(new Intent(Intent.ACTION_VIEW, Uri.parse(URI_PLAY_MUZEI)));
            }
        }
    }

    private void checkStoragePermission() {
        if (!PermissionUtils.hasSelfPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (isActive()) {
                view.requestMuzeiStoragePermission();
            }
        }
    }

    private boolean isMuzeiInstalled() {
        try {
            packageManager.getApplicationInfo(PKG_MUZEI, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
