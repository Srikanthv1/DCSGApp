package com.app.dcsg.Listener;

import com.app.dcsg.Model.ResponseModel.VenueResponseModel;

/**
 * Listener for the VenueDataCall event
 * Created by Srikanth on 1/02/18
 */
public interface VenueDataListener {

    void onSuccess(VenueResponseModel venueResponse);
    void onError(String errorMessage);
}
