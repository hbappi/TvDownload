package com.sttech.tvdownload;

import androidx.appcompat.app.AppCompatActivity;

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
import com.sttech.tvdownload.RestrofitResponses.contactRes;
import com.sttech.tvdownload.RestrofitResponses.privacyRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ContactUsActivity extends AppCompatActivity {

    API api;
    ImageView txthead;
//    TextView txtbody;
    TextView btnexit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);



        btnexit=findViewById(R.id.btnexit);
        btnexit.setFocusable(true);
//        btnexit.setFocusableInTouchMode(true);

        txthead=findViewById(R.id.txthead);
//        txtbody=findViewById(R.id.txtbody);
        api = ApiUtils.getAPI();

        GetDataApi();

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnexit.setBackgroundResource(R.drawable.rounded_borders);
                onBackPressed();
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


    public void GetDataApi() {
        Call<contactRes> call = api.CONTACTUS_RES_CALL("1");
        call.enqueue(new Callback<contactRes>() {
            @Override
            public void onResponse(Call<contactRes> call, retrofit2.Response<contactRes> response) {
                Log.e("api ", "\n"+response.toString());
                if (response.isSuccessful()) {

                    contactRes userdata = response.body();
                    try {

                        if(userdata!=null) {

                            if (userdata != null) {

//                                Glide.with(ContactUsActivity.this)
//                                        .load("https://"+userdata.getContactUsImage())
//                                        .placeholder(R.drawable.loading)
//                                        .into(txthead);
                                Picasso.get().load("https://"+userdata.getContactUsImage()).into(txthead);
//                                txthead.loadData(userdata.getContactUsImage(), "text/html", "UTF-8");
//                                txtbody.loadData(userdata.get(0).getContactUs(), "text/html", "UTF-8");
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish(), Html.FROM_HTML_MODE_COMPACT));
//                                    txtbody.setText(Html.fromHtml(userdata.get(0).getContactUs(), Html.FROM_HTML_MODE_COMPACT));
//                                } else {
////                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish()));
//                                    txtbody.setText(Html.fromHtml(userdata.get(0).getContactUs()));
//                                }

                            }

                        } else {
                            Toast.makeText(ContactUsActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){}

                } else {
                    Log.wtf("SIGNUP_LOG", "ELSE");
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<contactRes> call, Throwable t) {
                Toast.makeText(ContactUsActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


    }


}