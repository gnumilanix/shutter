package com.milanix.shutter.core;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.milanix.shutter.view.DividerItemDecoration;
import com.milanix.shutter.view.ItemOffsetDecoration;

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

    /**
     * Loads given image url to the given view
     *
     * @param view        to load image to
     * @param uri         to load image from
     * @param placeHolder to load on error
     */
    @BindingAdapter(value = {"imageUrl", "placeHolder", "transformation"}, requireAll = false)
    public static void loadImage(ImageView view, Uri uri, Drawable placeHolder, String transformation) {
        final DrawableRequestBuilder<Uri> requestBuilder = Glide.with(view.getContext()).load(uri).error(placeHolder);

        if (!TextUtils.isEmpty(transformation)) {
            switch (transformation) {
                case "crop":
                    requestBuilder.centerCrop();
            }
        }
        requestBuilder.into(view);
    }

    /**
     * Loads given image url to the given view
     *
     * @param view        to load image to
     * @param url         to load image from
     * @param placeHolder to load on error
     */
    @BindingAdapter(value = {"imageUrl", "placeHolder", "transformation"}, requireAll = false)
    public static void loadImage(final SubsamplingScaleImageView view, String url, Drawable placeHolder, String transformation) {
        final BitmapRequestBuilder<String, Bitmap> requestBuilder = Glide.with(view.getContext()).load(url).asBitmap().error(placeHolder);

        if (!TextUtils.isEmpty(transformation)) {
            switch (transformation) {
                case "crop":
                    requestBuilder.centerCrop();
            }
        }

        requestBuilder.into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                view.setImage(ImageSource.bitmap(resource));
            }
        });
    }

    /**
     * Sets given time as a relative time string
     *
     * @param view         to set relative time text to
     * @param textInMillis relative to
     */
    @BindingAdapter(value = "textInMillis")
    public static void setRelativeTimeString(TextView view, long textInMillis) {
        view.setText(DateUtils.getRelativeTimeSpanString(textInMillis, System.currentTimeMillis(), 0L,
                DateUtils.FORMAT_ABBREV_ALL));
    }

    /**
     * Sets given drawable as {@link RecyclerView} divider
     *
     * @param view            to set divider to
     * @param dividerDrawable to set drawable as
     */
    @BindingAdapter(value = {"dividerItemDecoration"})
    public static void setDivideritemDecoration(RecyclerView view, Drawable dividerDrawable) {
        view.addItemDecoration(new DividerItemDecoration(dividerDrawable));
    }

    /**
     * Sets given dimen as {@link RecyclerView} divider offset
     *
     * @param view     to set divider to
     * @param dimenRes to set dimen as
     */
    @BindingAdapter(value = {"itemOffsetDecoration"})
    public static void setItemOffsetDecoration(RecyclerView view, @DimenRes int dimenRes) {
        view.addItemDecoration(new ItemOffsetDecoration(view.getContext(), dimenRes));
    }
}
