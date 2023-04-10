package com.sttech.tvdownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.sttech.tvdownload.API.API;
import com.sttech.tvdownload.API.ApiUtils;
import com.sttech.tvdownload.RestrofitResponses.privacyRes;
import com.sttech.tvdownload.RestrofitResponses.selectRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PrivacyActivity extends AppCompatActivity {

    API api;
    ImageView txthead;
    ImageView txtbody;
    TextView btnesponal,btnexit;
    String engurl="",espurl="";
    boolean eng=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);



        btnesponal=findViewById(R.id.btnesponal);
        btnexit=findViewById(R.id.btnexit);

        btnesponal.setFocusable(true);
//        btnesponal.setFocusableInTouchMode(true);
        btnexit.setFocusable(true);
//        btnexit.setFocusableInTouchMode(true);

        txthead=findViewById(R.id.txthead);
        txtbody=findViewById(R.id.txtbody);
        api = ApiUtils.getAPI();

        if (savedInstanceState != null) {
            if(savedInstanceState.getString("eng").contains("false")){
                eng=false;
                btnesponal.setText("INGLES");
                btnexit.setText("Pulse Atrás para salir");
                txthead.setVisibility(View.GONE);
                txtbody.setVisibility(View.VISIBLE);
            }else {
                eng=true;
                btnesponal.setText("ESPANOL");
                btnexit.setText("Press BACK To EXIT");
                txthead.setVisibility(View.VISIBLE);
                txtbody.setVisibility(View.GONE);
            }
            engurl=savedInstanceState.getString("engurl");
            espurl=savedInstanceState.getString("espurl");
            Picasso.get().load(""+engurl).into(txthead);
            Picasso.get().load(""+espurl).into(txtbody);
        }else {
            GetDataApi();
        }


        btnesponal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnexit.setBackgroundResource(R.drawable.rounded_noborders);
                btnesponal.setBackgroundResource(R.drawable.rounded_borders);
                if(btnesponal.getText().toString().contains("ESPANOL")){
                    eng=false;
                    btnesponal.setText("INGLES");
                    btnexit.setText("Pulse Atrás para salir");
                    txthead.setVisibility(View.GONE);
                    txtbody.setVisibility(View.VISIBLE);
                }else {
                    eng=true;
                    btnesponal.setText("ESPANOL");
                    btnexit.setText("Press BACK To EXIT");
                    txthead.setVisibility(View.VISIBLE);
                    txtbody.setVisibility(View.GONE);
                }
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnesponal.setBackgroundResource(R.drawable.rounded_noborders);
                btnexit.setBackgroundResource(R.drawable.rounded_borders);
                onBackPressed();
            }
        });


        btnesponal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    btnesponal.setBackgroundResource(R.drawable.rounded_borders);
                }else {
                    btnesponal.setBackgroundResource(R.drawable.rounded_noborders);
                }
            }
        });
        btnexit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    btnexit.setBackgroundResource(R.drawable.rounded_borders);
                }else {
                    btnexit.setBackgroundResource(R.drawable.rounded_noborders);
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("eng", eng+"");
        outState.putString("engurl", engurl+"");
        outState.putString("espurl", espurl+"");
    }



    public void GetDataApi() {
        Call<privacyRes> call = api.PRIVACY_RES_CALL("1");
        call.enqueue(new Callback<privacyRes>() {
            @Override
            public void onResponse(Call<privacyRes> call, retrofit2.Response<privacyRes> response) {
                Log.e("api ", "\n"+response.toString());
                if (response.isSuccessful()) {

                    privacyRes userdata = response.body();
                    try {

                        if(userdata!=null) {

                            if (userdata != null) {
                                engurl="https://"+userdata.getPpEnglishImage();
                                espurl="https://"+userdata.getPpSpanishImage();

//                                Glide.with(PrivacyActivity.this)
//                                        .load("https://"+userdata.getPpEnglishImage())
//                                        .placeholder(R.drawable.loading)
//                                        .into(txthead);
//                                Glide.with(PrivacyActivity.this)
//                                        .load("https://"+userdata.getPpSpanishImage())
//                                        .placeholder(R.drawable.loading)
//                                        .into(txtbody);
                                Picasso.get().load("https://"+userdata.getPpEnglishImage()).into(txthead);
                                Picasso.get().load("https://"+userdata.getPpSpanishImage()).into(txtbody);

//                                txthead.loadData(userdata.get(0).getPrivacyPolicyEnglish(), "text/html", "UTF-8");
//                                txtbody.loadData(userdata.get(0).getPrivacyPolicySpanish(), "text/html", "UTF-8");
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish(), Html.FROM_HTML_MODE_COMPACT));
//                                    txtbody.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicySpanish(), Html.FROM_HTML_MODE_COMPACT));
//                                } else {
//                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish()));
//                                    txtbody.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicySpanish()));
//                                }

                            }

                        } else {
                            Toast.makeText(PrivacyActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){}

                } else {
                    Log.wtf("SIGNUP_LOG", "ELSE");
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<privacyRes> call, Throwable t) {
                Toast.makeText(PrivacyActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


    }




}