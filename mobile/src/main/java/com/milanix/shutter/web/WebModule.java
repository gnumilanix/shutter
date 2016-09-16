package com.milanix.shutter.web;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class WebModule {
    public static final String WEB_PAGE = "_page";

    private final WebPage webPage;

    public WebModule(WebPage webPage) {
        this.webPage = webPage;
    }

    @Provides
    @Named(WEB_PAGE)
    public WebPage provideWebPage() {
        return webPage;
    }
}
