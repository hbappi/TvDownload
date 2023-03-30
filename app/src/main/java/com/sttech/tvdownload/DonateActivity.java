package com.sttech.tvdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.sttech.tvdownload.API.API;
import com.sttech.tvdownload.API.ApiUtils;
import com.sttech.tvdownload.RestrofitResponses.contactRes;
import com.sttech.tvdownload.RestrofitResponses.donateRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DonateActivity extends AppCompatActivity {

    API api;
//    WebView txthead;
//    TextView txtbody;
    ImageView imgdonate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);




        imgdonate=findViewById(R.id.imgdonate);
//        txthead=findViewById(R.id.txthead);
//        txtbody=findViewById(R.id.txtbody);
        api = ApiUtils.getAPI();

        Glide.with(DonateActivity.this)
                .load("https://mctv.banttechenergies.com/admin_assets/images/donate.png")
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(imgdonate);

        GetDataApi();

        imgdonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent shareintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://bit.ly/3khAOs9"));
//                startActivity(shareintent);
            }
        });

    }


    public void GetDataApi() {
        Call<donateRes> call = api.DONATE_RES_CALL("1");
        call.enqueue(new Callback<donateRes>() {
            @Override
            public void onResponse(Call<donateRes> call, retrofit2.Response<donateRes> response) {
                Log.e("api ", "\n"+response.toString());
                if (response.isSuccessful()) {

                    donateRes userdata = response.body();
                    try {

                        if(userdata!=null) {

                            if (userdata != null) {

//                                Glide.with(DonateActivity.this)
//                                        .load("https://"+userdata.getDonateImage())
//                                        .placeholder(R.drawable.loading)
//                                        .into(imgdonate);
                                Picasso.get().load("https://"+userdata.getDonateImage()).into(imgdonate);
//                                txthead.loadData(userdata.get(0).getDonate(), "text/html", "UTF-8");
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish(), Html.FROM_HTML_MODE_COMPACT));
//                                    txtbody.setText(Html.fromHtml(userdata.get(0).getDonate(), Html.FROM_HTML_MODE_COMPACT));
//                                } else {
////                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish()));
//                                    txtbody.setText(Html.fromHtml(userdata.get(0).getDonate()));
//                                }

                            }

                        } else {
                            Toast.makeText(DonateActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){}

                } else {
                    Log.wtf("SIGNUP_LOG", "ELSE");
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<donateRes> call, Throwable t) {
                Toast.makeText(DonateActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


    }



}