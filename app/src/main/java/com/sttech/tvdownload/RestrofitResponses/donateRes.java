package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class donateRes {

    @SerializedName("donate_image")
    @Expose
    private String donateImage;

    public String getDonateImage() {
        return donateImage;
    }

    public void setDonateImage(String donateImage) {
        this.donateImage = donateImage;
    }

}
