package com.app.dcsg.DataProvider;

import com.app.dcsg.Listener.CustomCallListener;
import com.app.dcsg.Listener.VenueDataListener;
import com.app.dcsg.Model.ResponseModel.VenueResponseModel;
import com.app.dcsg.Networking.CustomCallback;
import com.app.dcsg.Networking.NetworkErrorParser;
import com.app.dcsg.Networking.ServiceCall;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by srikanth
 */

public class VenueDataProvider {

    public void fetchVenueDetails(final VenueDataListener venueDataListener) {

        //Trigger an Async Network call to fetch the JSON data and pass it as a response using Retrofit2

        Call<VenueResponseModel> networkCall = ServiceCall.getNetworkService().getVenueDetails();
        networkCall.enqueue(new CustomCallback<VenueResponseModel>(new CustomCallListener() {
            @Override
            public void onSuccess(Response response) {
                //Call is successful
                VenueResponseModel responseModel = (VenueResponseModel) response.body();
                if (venueDataListener != null) {
                    venueDataListener.onSuccess(responseModel);
                }
            }

            @Override
            public void onError(NetworkErrorParser errorParser) {
                //Call failed with error response
                if (venueDataListener != null) {
                    venueDataListener.onError(errorParser.getNetworkError().getMessage());
                }

            }
        }));
    }

}
