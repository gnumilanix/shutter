package com.milanix.shutter.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.milanix.shutter.core.specification.IStore;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmQuery;

/**
 * Helper class for some generic realm methods
 *
 * @author milan
 */
public class RealmHelper {
    /**
     * Invokes callback. Invokes {@link IStore.Callback#onSuccess(T)} if data is non null, otherwise {@link IStore.Callback#onFailure(Throwable)}
     *
     * @param data     to test
     * @param callback to invoke
     * @param <T>      of the data returned by the callback
     */
    public static <T> void invokeCallback(@Nullable T data, @Nullable IStore.Callback<T> callback) {
        if (null != callback) {
            if (null != data)
                callback.onSuccess(data);
            else
                callback.onFailure(new NullPointerException("Data not available"));
        }
    }

    /**
     * Appends where clause to given field values
     *
     * @param where  query to build upon
     * @param field  to query
     * @param values to query for
     * @param <T>    of the data the query is being appended for
     * @return new query
     */
    public static <T extends RealmModel> RealmQuery<T> where(@NonNull RealmQuery<T> where, @NonNull String field,
                                                             @NonNull List<Long> values) {
        for (int i = 0, size = values.size(); i < size; i++) {
            if (i > 0)
                where.or();

            where.equalTo(field, values.get(i));
        }

        return where;
    }
}
