package com.milanix.shutter.web;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing web page dependencies
 *
 * @author milan
 */
@Module
public class WebPageModule {
    public static final String WEB_PAGE = "_page";

    private final WebPageContract.View view;
    private final WebPage webPage;

    public WebPageModule(WebPageContract.View view, WebPage webPage) {
        this.view = view;
        this.webPage = webPage;
    }

    @Provides
    @Named(WEB_PAGE)
    public WebPage provideWebPage() {
        return webPage;
    }

    @Provides
    public WebPageContract.View provideView() {
        return view;
    }

    @Provides
    public WebPageContract.Presenter providePresenter(WebPageContract.View view,
                                                      @Named(WEB_PAGE) WebPage webPage) {
        return new WebPagePresenter(view, webPage);
    }
}
