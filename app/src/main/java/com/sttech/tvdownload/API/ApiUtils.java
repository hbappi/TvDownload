package com.sttech.tvdownload.API;


public class ApiUtils {
        public static API getAPI(){
            return RestClient.getClient().create(API.class);
        }
}
