package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class donateRes {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("donate")
    @Expose
    private String donate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDonate() {
        return donate;
    }

    public void setDonate(String donate) {
        this.donate = donate;
    }


}
