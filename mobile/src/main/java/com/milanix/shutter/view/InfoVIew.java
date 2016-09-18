package com.milanix.shutter.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.milanix.shutter.R;

/**
 * View that provides info including text and icon in error view pattern
 *
 * @author milan
 */
public class InfoView extends RelativeLayout {
    private int bgColor;
    private Drawable icon;
    private String text;
    private int textColor;

    public InfoView(Context context) {
        this(context, null);
    }

    public InfoView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public InfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        setup();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
        setup();
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.infoView);

            bgColor = array.getColor(R.styleable.infoView_info_bg_color, Color.WHITE);
            icon = array.getDrawable(R.styleable.infoView_info_icon);
            text = array.getString(R.styleable.infoView_info_text);
            textColor = array.getColor(R.styleable.infoView_info_text_color, Color.GRAY);

            array.recycle();
        }
    }

    public void setup() {
        final int padding = getResources().getDimensionPixelSize(R.dimen.padding_large);
        final RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        final TextView textView = new TextView(getContext());
        textView.setId(R.id.info_view_text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_small));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        final RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        iconParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.margin_normal);
        iconParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        iconParams.addRule(RelativeLayout.ABOVE, R.id.info_view_text);

        final ImageView imageView = new ImageView(getContext());
        imageView.setId(R.id.info_view_icon);
        imageView.setImageDrawable(icon);

        setPadding(padding, padding, padding, padding);
        setBackgroundColor(bgColor);
        addView(textView, textParams);
        addView(imageView, iconParams);
    }
}
