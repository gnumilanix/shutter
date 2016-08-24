package com.milanix.shutter.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.milanix.shutter.R;

/**
 * Extension of {@link FrameLayout} with height proportional to it's weight
 *
 * @author milan
 */
public class ProportionalFrameLayout extends FrameLayout {

    private float proportion;

    public ProportionalFrameLayout(Context context) {
        super(context, null);
    }

    public ProportionalFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionalFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProportionalFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs, defStyleAttr);
    }

    private void setAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.proportion, defStyleAttr, 0);
        proportion = a.getFloat(R.styleable.proportion_proportion, 0.75f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int) (width * proportion), MeasureSpec.EXACTLY));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
