package com.app.dcsg.Listener;

import com.app.dcsg.Networking.NetworkErrorParser;

import retrofit2.Response;

/**
 * Created by srikanth
 */

//Listener for the Network Call event
public interface CustomCallListener {

    void onSuccess(Response response);
    void onError(NetworkErrorParser errorParser);
}
