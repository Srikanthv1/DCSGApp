
package com.app.dcsg.Model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Contact as Response
 * Created by Srikanth on 1/02/18
 */
public class Contact {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("facebookName")
    @Expose
    private String facebookName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Contact() {
    }

    /**
     * 
     * @param twitter
     * @param phone
     * @param facebookName
     * @param facebook
     */
    public Contact(String phone, String twitter, String facebook, String facebookName) {
        super();
        this.phone = phone;
        this.twitter = twitter;
        this.facebook = facebook;
        this.facebookName = facebookName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

}
