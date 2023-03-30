package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class helpRes {
    @SerializedName("hi_english_image")
    @Expose
    private String hiEnglishImage;
    @SerializedName("hi_spanish_image")
    @Expose
    private String hiSpanishImage;

    public String getHiEnglishImage() {
        return hiEnglishImage;
    }

    public void setHiEnglishImage(String hiEnglishImage) {
        this.hiEnglishImage = hiEnglishImage;
    }

    public String getHiSpanishImage() {
        return hiSpanishImage;
    }

    public void setHiSpanishImage(String hiSpanishImage) {
        this.hiSpanishImage = hiSpanishImage;
    }
}
