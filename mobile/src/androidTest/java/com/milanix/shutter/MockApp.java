package com.milanix.shutter;

import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.user.UserComponent;

/**
 * Created by milan on 15/9/2016.
 */

public class MockApp extends App{

    @Override
    public AppComponent getAppComponent() {
        return super.getAppComponent();
    }

    @Override
    public UserComponent getUserComponent() {
        return super.getUserComponent();
    }
}
