package com.app.dcsg.Networking;

import com.app.dcsg.Model.NetworkError;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by srikanth on 1/30/2018.
 */

public class NetworkErrorParser {

    private static final String LOG_TAG = "NetworkErrorParser";
    private NetworkError networkError;

    public NetworkErrorParser(Response responseBody) {
        networkError = parseErrorMessage(responseBody);
    }

    NetworkErrorParser(Throwable throwable) {
        networkError = new NetworkError();
        networkError.setMessage("Could not connect to Internet. Please Check the connection");
    }

    private NetworkError parseErrorMessage(Response response) {
        networkError = new NetworkError();
        if (response != null) {
            @SuppressWarnings("unchecked")
            Response<ResponseBody> body = (Response<ResponseBody>) response;

            networkError.setMessage("Server Error. Please try again");
        } else {
            networkError.setMessage("Error Occurred. Please try again");
        }

        return networkError;
    }

    public NetworkError getNetworkError() {
        return networkError;
    }

}
