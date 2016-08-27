package com.milanix.shutter.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.milanix.shutter.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.milanix.shutter.view.ProportionalFrameLayout.ProportionTo.HORIZONTAL;
import static com.milanix.shutter.view.ProportionalFrameLayout.ProportionTo.VERTICAL;

/**
 * Extension of {@link FrameLayout} with height proportional to it's weight
 *
 * @author milan
 */
public class ProportionalFrameLayout extends FrameLayout {

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProportionTo {
        int HORIZONTAL = 0;
        int VERTICAL = 1;
    }

    private float proportion;
    private int proportionTo;

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
        proportionTo = a.getInt(R.styleable.proportion_proportion_to, ProportionTo.HORIZONTAL);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (proportionTo) {
            case ProportionTo.VERTICAL:
                final int height = MeasureSpec.getSize(heightMeasureSpec);
                super.onMeasure(MeasureSpec.makeMeasureSpec((int) (height * proportion), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                break;
            default:
                final int width = MeasureSpec.getSize(widthMeasureSpec);
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((int) (width * proportion), MeasureSpec.EXACTLY));
                break;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
