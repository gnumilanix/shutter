package com.milanix.shutter.web;

import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * Web page presenter
 *
 * @author milan
 */
public class WebPagePresenter extends AbstractPresenter<WebPageContract.View> implements WebPageContract.Presenter {
    private final WebPage webPage;

    @Inject
    public WebPagePresenter(WebPageContract.View view, WebPage webPage) {
        super(view);
        this.webPage = webPage;
    }

    @Override
    public void subscribe() {
        super.subscribe();

        if (isActive()) {
            view.loadUrl(webPage);
        }
    }
}
