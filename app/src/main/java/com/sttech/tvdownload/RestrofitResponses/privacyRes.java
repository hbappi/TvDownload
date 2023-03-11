package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class privacyRes {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("privacy_policy_english")
    @Expose
    private String privacyPolicyEnglish;
    @SerializedName("privacy_policy_spanish")
    @Expose
    private String privacyPolicySpanish;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrivacyPolicyEnglish() {
        return privacyPolicyEnglish;
    }

    public void setPrivacyPolicyEnglish(String privacyPolicyEnglish) {
        this.privacyPolicyEnglish = privacyPolicyEnglish;
    }

    public String getPrivacyPolicySpanish() {
        return privacyPolicySpanish;
    }

    public void setPrivacyPolicySpanish(String privacyPolicySpanish) {
        this.privacyPolicySpanish = privacyPolicySpanish;
    }
}
