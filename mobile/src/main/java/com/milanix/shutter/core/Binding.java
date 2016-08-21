package com.milanix.shutter.core;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

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
    @BindingAdapter(value = {"imageUrl", "placeHolder"},requireAll = false)
    public static void loadImage(ImageView view, String url, Drawable placeHolder) {
        Glide.with(view.getContext()).load(url).error(placeHolder).into(view);
    }
}
