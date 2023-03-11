package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class contactRes {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("contact_us")
    @Expose
    private String contactUs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }


}
