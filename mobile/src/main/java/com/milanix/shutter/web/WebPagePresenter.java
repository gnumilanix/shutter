package com.milanix.shutter.web;

import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * New post presenter
 *
 * @author milan
 */
public class WebPagePresenter extends AbstractPresenter<WebPageContract.View> implements WebPageContract.Presenter {
    private WebPage webPage;

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
