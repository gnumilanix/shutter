package com.milanix.shutter.web;

/**
 * Builder class that builds a {@link WebPage}
 *
 * @author milan
 */
public class WebPageBuilder {
    private String title;
    private String url;

    public WebPageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public WebPageBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public WebPage build() {
        return new WebPage(title, url);
    }
}