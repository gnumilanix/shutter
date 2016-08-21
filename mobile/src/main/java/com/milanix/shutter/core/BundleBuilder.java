package com.milanix.shutter.core;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that allows creation of {@link Bundle} with builder like creation
 *
 * @author milan
 */
public class BundleBuilder {
    final Bundle bundle;

    /**
     * Default constructor that creates a new bundle
     */
    public BundleBuilder() {
        this.bundle = new Bundle();
    }

    /**
     * Constructor that takes in a bundle and works on it.
     *
     * @param bundle to work with. If null will create a new bundle
     */
    public BundleBuilder(@Nullable Bundle bundle) {
        if (null == bundle)
            this.bundle = new Bundle();
        else
            this.bundle = new Bundle(bundle);
    }

    public BundleBuilder putAll(Bundle bundle) {
        this.bundle.putAll(bundle);

        return this;
    }

    public BundleBuilder putBoolean(String key, boolean value) {
        bundle.putBoolean(key, value);

        return this;
    }

    public BundleBuilder putInt(String key, int value) {
        bundle.putInt(key, value);

        return this;
    }

    public BundleBuilder putLong(String key, long value) {
        bundle.putLong(key, value);

        return this;
    }

    public BundleBuilder putDouble(String key, double value) {
        bundle.putDouble(key, value);

        return this;
    }

    public BundleBuilder putString(String key, String value) {
        bundle.putString(key, value);

        return this;
    }

    public BundleBuilder putBooleanArray(String key, boolean[] value) {
        bundle.putBooleanArray(key, value);

        return this;
    }

    public BundleBuilder putIntArray(String key, int[] value) {
        bundle.putIntArray(key, value);

        return this;
    }

    public BundleBuilder putLongArray(String key, long[] value) {
        bundle.putLongArray(key, value);

        return this;
    }

    public BundleBuilder putDoubleArray(String key, double[] value) {
        bundle.putDoubleArray(key, value);

        return this;
    }

    public BundleBuilder putStringArray(String key, String[] value) {
        bundle.putStringArray(key, value);

        return this;
    }

    public BundleBuilder putByte(String key, byte value) {
        bundle.putByte(key, value);

        return this;
    }

    public BundleBuilder putChar(String key, char value) {
        bundle.putChar(key, value);

        return this;
    }

    public BundleBuilder putShort(String key, short value) {
        bundle.putShort(key, value);

        return this;
    }

    public BundleBuilder putFloat(String key, float value) {
        bundle.putFloat(key, value);

        return this;
    }

    public BundleBuilder putCharSequence(String key, CharSequence value) {
        bundle.putCharSequence(key, value);

        return this;
    }

    public BundleBuilder putParcelable(String key, Parcelable value) {
        bundle.putParcelable(key, value);

        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder putSize(String key, Size value) {
        bundle.putSize(key, value);

        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder putSizeF(String key, SizeF value) {
        bundle.putSizeF(key, value);

        return this;
    }

    public BundleBuilder putParcelableArray(String key, Parcelable[] value) {
        bundle.putParcelableArray(key, value);

        return this;
    }

    public BundleBuilder putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);

        return this;
    }

    public BundleBuilder putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);

        return this;
    }

    public BundleBuilder putIntegerArrayList(String key, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);

        return this;
    }

    public BundleBuilder putStringArrayList(String key, ArrayList<String> value) {
        bundle.putStringArrayList(key, value);

        return this;
    }

    public BundleBuilder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);

        return this;
    }

    public BundleBuilder putSerializable(String key, Serializable value) {
        bundle.putSerializable(key, value);

        return this;
    }

    public BundleBuilder putByteArray(String key, byte[] value) {
        bundle.putByteArray(key, value);

        return this;
    }

    public BundleBuilder putShortArray(String key, short[] value) {
        bundle.putShortArray(key, value);

        return this;
    }

    public BundleBuilder putCharArray(String key, char[] value) {
        bundle.putCharArray(key, value);

        return this;
    }

    public BundleBuilder putFloatArray(String key, float[] value) {
        bundle.putFloatArray(key, value);

        return this;
    }

    public BundleBuilder putCharSequenceArray(String key, CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);

        return this;
    }

    public BundleBuilder putBundle(String key, Bundle value) {
        bundle.putBundle(key, value);

        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public BundleBuilder putBinder(String key, IBinder value) {
        bundle.putBinder(key, value);

        return this;
    }

    /**
     * Returns a  bundle created here
     *
     * @return bundle
     */
    @NonNull
    public Bundle build() {
        return bundle;
    }
}
