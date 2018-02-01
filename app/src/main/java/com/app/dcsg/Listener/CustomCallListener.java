package com.app.dcsg.Listener;

import com.app.dcsg.Networking.NetworkErrorParser;

import retrofit2.Response;

/**
 * Created by srika on 1/30/2018.
 */

public interface CustomCallListener {

    void onSuccess(Response response);
    void onError(NetworkErrorParser errorParser);
}
