package com.milanix.shutter.settings;

import android.content.Intent;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract defining settings related view/presenter implementations
 *
 * @author milan
 */
public interface SettingsContract {
    interface View extends IView {
        void requestMuzeiStoragePermission();

        void requestMuzeiInstallation();

        void installMuzei(Intent intent);

        void cannotEnableMuzei();
    }

    interface Presenter extends IPresenter {
        void initMuzeiSettings();

        void validateMuzeiSettings();

        void handleMuzeiNotInstalled();
    }
}
