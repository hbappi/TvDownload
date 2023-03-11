package com.sttech.tvdownload.RestrofitResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class selectRes {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
