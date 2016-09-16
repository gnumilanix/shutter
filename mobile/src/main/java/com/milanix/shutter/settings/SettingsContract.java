package com.milanix.shutter.settings;

import android.content.Intent;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for new post related implementations
 *
 * @author milan
 */
public interface SettingsContract {
    interface View extends IView {
        void requestMuzeiStoragePermission();

        void requestMuzeiInstallation();

        void installMuzei(Intent intent);
    }

    interface Presenter extends IPresenter {
        void validateMuzeiSettings();

        void handleMuzeiNotInstalled();
    }
}
