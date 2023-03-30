package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class contactRes {

    @SerializedName("contact_us_image")
    @Expose
    private String contactUsImage;

    public String getContactUsImage() {
        return contactUsImage;
    }

    public void setContactUsImage(String contactUsImage) {
        this.contactUsImage = contactUsImage;
    }

}
