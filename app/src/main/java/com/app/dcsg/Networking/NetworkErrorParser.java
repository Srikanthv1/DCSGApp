package com.app.dcsg.Networking;

import com.app.dcsg.Model.NetworkError;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by srikanth
 */

public class NetworkErrorParser {

    private static final String LOG_TAG = "NetworkErrorParser";
    private NetworkError mNetworkError;

    public NetworkErrorParser(Response responseBody) {
        mNetworkError = parseErrorMessage(responseBody);
    }

    NetworkErrorParser(Throwable throwable) {
        mNetworkError = new NetworkError();
        mNetworkError.setMessage("Could not connect to Internet. Please Check the connection");
    }

    private NetworkError parseErrorMessage(Response response) {
        mNetworkError = new NetworkError();
        if (response != null) {
            @SuppressWarnings("unchecked")
            Response<ResponseBody> body = (Response<ResponseBody>) response;

            mNetworkError.setMessage("Server Error. Please try again");
        } else {
            mNetworkError.setMessage("Error Occurred. Please try again");
        }

        return mNetworkError;
    }

    public NetworkError getNetworkError() {
        return mNetworkError;
    }

}
