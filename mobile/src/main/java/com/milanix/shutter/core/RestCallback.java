package com.milanix.shutter.core;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Extension of retrofit callback that will mark http errors as failure
 *
 * @author milan
 */
public abstract class RestCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful())
            onFailure(call, new Exception("HTTP error code was " + response.code()));
        else if (null == response.body())
            onFailure(call, new NullPointerException("Body was null"));
        else
            onResponse(response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(t);
    }

    abstract public void onResponse(Response<T> response);

    abstract public void onFailure(Throwable t);

}
