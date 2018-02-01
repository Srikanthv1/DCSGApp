package com.app.dcsg.Model.ResponseModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by srikanth on 1/30/2018.
 */


public class VenueResponseModel {

    @SerializedName("venues")
    @Expose
    private List<Venue> venues = null;

    /**
     * No args constructor for use in serialization
     */
    public VenueResponseModel() {
    }

    /**
     * @param venues
     */
    public VenueResponseModel(List<Venue> venues) {
        super();
        this.venues = venues;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}

