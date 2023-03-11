package com.sttech.tvdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sttech.tvdownload.API.API;
import com.sttech.tvdownload.API.ApiUtils;
import com.sttech.tvdownload.RestrofitResponses.contactRes;
import com.sttech.tvdownload.RestrofitResponses.privacyRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ContactUsActivity extends AppCompatActivity {

    API api;
    TextView txthead;
    TextView txtbody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);



        txthead=findViewById(R.id.txthead);
        txtbody=findViewById(R.id.txtbody);
        api = ApiUtils.getAPI();

        GetDataApi();

    }


    public void GetDataApi() {
        Call<List<contactRes>> call = api.CONTACTUS_RES_CALL("1");
        call.enqueue(new Callback<List<contactRes>>() {
            @Override
            public void onResponse(Call<List<contactRes>> call, retrofit2.Response<List<contactRes>> response) {
                Log.e("api ", "\n"+response.toString());
                if (response.isSuccessful()) {

                    List<contactRes> userdata = response.body();
                    try {

                        if(userdata!=null) {

                            if (userdata.size() != 0) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish(), Html.FROM_HTML_MODE_COMPACT));
                                    txtbody.setText(Html.fromHtml(userdata.get(0).getContactUs(), Html.FROM_HTML_MODE_COMPACT));
                                } else {
//                                    txthead.setText(Html.fromHtml(userdata.get(0).getPrivacyPolicyEnglish()));
                                    txtbody.setText(Html.fromHtml(userdata.get(0).getContactUs()));
                                }

                            }

                        } else {
                            txthead.setText("Not Found");
                        }

                    }catch (Exception e){}

                } else {
                    Log.wtf("SIGNUP_LOG", "ELSE");
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<contactRes>> call, Throwable t) {
                Toast.makeText(ContactUsActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


    }



}