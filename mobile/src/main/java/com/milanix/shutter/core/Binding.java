package com.milanix.shutter.core;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

/**
 * Class containing generic {@link BindingAdapter}
 *
 * @author milan
 */
public class Binding {
    /**
     * Loads given image url to the given view
     *
     * @param view        to load image to
     * @param url         to load image from
     * @param placeHolder to load on error
     */
    @BindingAdapter(value = {"imageUrl", "placeHolder", "transformation"}, requireAll = false)
    public static void loadImage(ImageView view, String url, Drawable placeHolder, String transformation) {
        final DrawableRequestBuilder<String> requestBuilder = Glide.with(view.getContext()).load(url).error(placeHolder);

        if (!TextUtils.isEmpty(transformation)) {
            switch (transformation) {
                case "crop":
                    requestBuilder.centerCrop();
            }
        }
        requestBuilder.into(view);
    }
}
