package com.app.dcsg.Networking;

import com.app.dcsg.Listener.CustomCallListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Srikanth on 1/02/18
 */

public class CustomCallback<Object> implements Callback<Object> {

    private CustomCallListener mCustomCall;

    public CustomCallback(CustomCallListener mCustomCall) {
        this.mCustomCall = mCustomCall;
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        if (response.isSuccessful()) {
            mCustomCall.onSuccess(response);
        } else {
            mCustomCall.onError(new NetworkErrorParser(response));
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable throwable) {
        mCustomCall.onError(new NetworkErrorParser(throwable));
    }


}
