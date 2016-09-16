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
public class WebPageModule {
    public static final String WEB_PAGE = "_page";

    private final WebPage webPage;

    public WebPageModule(WebPage webPage) {
        this.webPage = webPage;
    }

    @Provides
    @Named(WEB_PAGE)
    public WebPage provideWebPage() {
        return webPage;
    }
}
