package com.milanix.shutter.settings;

import android.content.Context;
import android.content.pm.PackageManager;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing new post related dependencies
 *
 * @author milan
 */
@Module
public class SettingsModule {
    private final SettingsContract.View view;

    public SettingsModule(SettingsContract.View view) {
        this.view = view;
    }

    @Provides
    public SettingsContract.View provideView() {
        return view;
    }

    @Provides
    public SettingsContract.Presenter providePresenter(SettingsContract.View view,
                                                       Context context,
                                                       PackageManager packageManager) {
        return new SettingsPresenter(view, context, packageManager);
    }
}
