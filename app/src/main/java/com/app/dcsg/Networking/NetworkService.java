package com.app.dcsg.Networking;

import com.app.dcsg.Model.ResponseModel.VenueResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface for declaring Web API Calls
 * Created by Srikanth on 1/02/18
 */

public interface NetworkService {

    @GET(Url.VENUE)
    Call<VenueResponseModel> getVenueDetails();
}
