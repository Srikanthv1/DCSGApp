
package com.app.dcsg.Model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("photoId")
    @Expose
    private String photoId;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Photo() {
    }

    /**
     * 
     * @param photoId
     * @param createdAt
     * @param url
     */
    public Photo(String photoId, Long createdAt, String url) {
        super();
        this.photoId = photoId;
        this.createdAt = createdAt;
        this.url = url;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
