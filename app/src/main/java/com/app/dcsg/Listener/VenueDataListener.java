package com.app.dcsg.Listener;

import com.app.dcsg.Model.ResponseModel.VenueResponseModel;

/**
 * Created by srikanth
 */
//Listener for the VenueDataCall event
public interface VenueDataListener {

    void onSuccess(VenueResponseModel venueResponse);
    void onError(String errorMessage);
}
