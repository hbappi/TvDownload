package com.sttech.tvdownload.API;


import com.sttech.tvdownload.RestrofitResponses.contactRes;
import com.sttech.tvdownload.RestrofitResponses.donateRes;
import com.sttech.tvdownload.RestrofitResponses.helpRes;
import com.sttech.tvdownload.RestrofitResponses.privacyRes;
import com.sttech.tvdownload.RestrofitResponses.selectRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("api/getdata")
    Call<List<selectRes>> SELECT_RES_CALL(@Field("keyword") String keyword);


    @FormUrlEncoded
    @POST("api/getdata/helpinstruction")
    Call<List<helpRes>> HELP_RES_CALL(@Field("id") String keyword);



    @FormUrlEncoded
    @POST("api/getdata/privacypolicy")
    Call<List<privacyRes>> PRIVACY_RES_CALL(@Field("id") String keyword);


    @FormUrlEncoded
    @POST("api/getdata/contactus")
    Call<List<contactRes>> CONTACTUS_RES_CALL(@Field("id") String keyword);

    @FormUrlEncoded
    @POST("api/getdata/donate")
    Call<List<donateRes>> DONATE_RES_CALL(@Field("id") String keyword);

}


