package com.milanix.shutter.settings;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds new post detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = SettingsModule.class)
public interface SettingsComponent {
    void inject(SettingsFragment fragment);
}
