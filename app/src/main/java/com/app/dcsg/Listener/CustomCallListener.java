package com.app.dcsg.Listener;

import com.app.dcsg.Networking.NetworkErrorParser;

import retrofit2.Response;

/**
 * Listener for the Network Call event
 * Created by Srikanth on 1/02/18
 */

public interface CustomCallListener {

    void onSuccess(Response response);
    void onError(NetworkErrorParser errorParser);
}
