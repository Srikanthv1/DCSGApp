package com.app.dcsg.Networking;

import com.app.dcsg.Model.ResponseModel.VenueResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by srikanth on 1/30/2018.
 */

public interface NetworkService {

    @GET(Url.VENUE)
    Call<VenueResponseModel> getVenueDetails();
}