package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class privacyRes {

    @SerializedName("pp_english_image")
    @Expose
    private String ppEnglishImage;
    @SerializedName("pp_spanish_image")
    @Expose
    private String ppSpanishImage;

    public String getPpEnglishImage() {
        return ppEnglishImage;
    }

    public void setPpEnglishImage(String ppEnglishImage) {
        this.ppEnglishImage = ppEnglishImage;
    }

    public String getPpSpanishImage() {
        return ppSpanishImage;
    }

    public void setPpSpanishImage(String ppSpanishImage) {
        this.ppSpanishImage = ppSpanishImage;
    }

}
