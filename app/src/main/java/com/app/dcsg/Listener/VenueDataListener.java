package com.app.dcsg.Listener;

import com.app.dcsg.Model.ResponseModel.VenueResponseModel;

/**
 * Created by srikanth on 1/30/2018.
 */

public interface VenueDataListener {

    void onSuccess(VenueResponseModel venueResponse);
    void onError(String errorMessage);
}
