package com.milanix.shutter.web;

import org.parceler.Parcel;

/**
 * Model defining web page
 *
 * @author milan
 */
@Parcel
public class WebPage {
    public String title;
    public String url;

    public WebPage() {
    }

    public WebPage(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
