package com.sttech.tvdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        btnesponal=findViewById(R.id.btnesponal);
        btnexit=findViewById(R.id.btnexit);

        txthead=findViewById(R.id.txthead);
        txtbody=findViewById(R.id.txtbody);
        api = ApiUtils.getAPI();

        GetDataApi();

        btnesponal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnesponal.getText().toString().contains("ESPANOL")){
                    btnesponal.setText("INGLES");
                    btnexit.setText("Pulse Atrás para salir");
                    txthead.setVisibility(View.GONE);
                    txtbody.setVisibility(View.VISIBLE);
                }else {
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
                onBackPressed();
            }
        });

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