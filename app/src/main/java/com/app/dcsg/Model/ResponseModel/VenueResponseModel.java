package com.app.dcsg.Model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for List of Venues as Response
 * Created by Srikanth on 1/02/18
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

