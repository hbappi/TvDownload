package com.sttech.tvdownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.PowerManager;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sttech.tvdownload.API.API;
import com.sttech.tvdownload.API.ApiUtils;
import com.sttech.tvdownload.RestrofitResponses.selectRes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    EditText edtcode;
    Button btndownload,btnfileexplorer,btnhelp,btnprivacy;
    RelativeLayout relcode,reldownload,relfileexplorer,relhelp,relprivacy;
    API api;
    String url;
    int focus=0;
    ProgressDialog mProgressDialog;
    DownloadTask downloadTask;
    public DrawerLayout drawerLayout;
    public NavigationView nav_menu;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    boolean codeok=false;
    boolean downloadfocus=false;
    boolean fileexplorerfocus=false;
    boolean helpfocus=false;
    boolean privacyfocus=false;
    boolean codefocus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
//        startActivity(intent);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_menu = findViewById(R.id.nav_menu);
        nav_menu.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        api = ApiUtils.getAPI();
        edtcode=findViewById(R.id.edtcode);

        relcode=findViewById(R.id.relcode);
        reldownload=findViewById(R.id.reldownload);
        relfileexplorer=findViewById(R.id.relfileexplorer);
        relhelp=findViewById(R.id.relhelp);
        relprivacy=findViewById(R.id.relprivacy);

        btndownload=findViewById(R.id.btndownload);
        btndownload.setFocusable(true);
//        btndownload.setFocusableInTouchMode(true);
        btnfileexplorer=findViewById(R.id.btnfileexplorer);
        btnfileexplorer.setFocusable(true);
//        btnfileexplorer.setFocusableInTouchMode(true);
        btnhelp=findViewById(R.id.btnhelp);
        btnhelp.setFocusable(true);
//        btnhelp.setFocusableInTouchMode(true);
        btnprivacy=findViewById(R.id.btnprivacy);
        btnprivacy.setFocusable(true);
//        btnprivacy.setFocusableInTouchMode(true);

        btnfileexplorer.setEnabled(false);

        edtcode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(MainActivity.this, s+" "+start+" "+count, Toast.LENGTH_SHORT).show();
                codeok=false;
                if(s.length()>=6) {
                    relcode.setBackgroundResource(R.drawable.rounded_edittexto);
                    GetDataApi(s.toString());
                }else {
                    relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    focus=0;
                    url=null;
                }
            }
        });

        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url != null) {
                    if (checkPermissionForReadExtertalStorage()) {

                        downloadTask = new DownloadTask(MainActivity.this);
                        downloadTask.execute("" + url);
//                downloadTask.execute("https://d-04.winudf.com/b/APK/Y29tLmluc3RhZ3JhbS5saXRlXzQ1MDI0OTI0MV82YmU3ZjBhYg?_fn=SW5zdGFncmFtIExpdGVfMzQ0LjAuMC4xMS44M19BcGtwdXJlLmFwaw&_p=Y29tLmluc3RhZ3JhbS5saXRl&download_id=1412501676346857&is_hot=true&k=290422941bdd1697603b1b6e5f5ec32664047145");
//                downloadTask.execute("https://banttech.com/CineplexHD.apk");
//                        url = null;

                    } else {
                        try {
                            requestPermissionForReadExtertalStorage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnfileexplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,FileExplorerActivity.class);
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        btnprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PrivacyActivity.class);
                startActivity(intent);
            }
        });

        btnhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);


        edtcode.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(checkPermissionForReadExtertalStorage()) {
        }else {
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void GetDataApi(String code) {
        Call<List<selectRes>> call = api.SELECT_RES_CALL(code);
        call.enqueue(new Callback<List<selectRes>>() {
            @Override
            public void onResponse(Call<List<selectRes>> call, retrofit2.Response<List<selectRes>> response) {
                Log.e("api ", "\n"+response.toString());
                if (response.isSuccessful()) {

                    List<selectRes> userdata = response.body();
                    try {

                  if(userdata!=null) {

                      if (userdata.size() != 0 && !userdata.get(0).getKeyword().equals("Not Found")) {
                          relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                          reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                          url = "" + userdata.get(0).getUrl();
                          if(!url.contains("https://")){
                              url="https://"+url;
                          }
                          focus=1;
                          codeok=true;
                          hideKeyboard(MainActivity.this);
                          relcode.clearFocus();
                          btndownload.requestFocus();
//                          btnfileexplorer.setEnabled(false);
//                          Toast.makeText(MainActivity.this, "" + userdata.get(0).getUrl(), Toast.LENGTH_SHORT).show();
                      } else {
                          relcode.setBackgroundResource(R.drawable.rounded_edittextred);
                          reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                          focus=0;
                          url = null;
                      }

                  } else {
                      relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                      reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                      focus=0;
                      url = null;
                  }

                    }catch (Exception e){}

                } else {
                    Log.wtf("SIGNUP_LOG", "ELSE");
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<selectRes>> call, Throwable t) {
                relcode.setBackgroundResource(R.drawable.rounded_edittextg);
//                Log.wtf("signuplog", "failure" + t.getMessage());
//                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("debug", "we are here");
//        if(keyCode==24){
//            keyCode=19;
//        }else if(keyCode==25){
//            keyCode=20;
//        }else if(keyCode==3){
//            keyCode=21;
//        }else if(keyCode==4){
//            keyCode=22;
//        }
//        Toast.makeText(this, ""+keyCode, Toast.LENGTH_SHORT).show();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:

                if(btndownload.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 1");
                    downloadfocus=false; codefocus=true;
                    btndownload.clearFocus();
                    edtcode.requestFocus();
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else if(btnfileexplorer.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 2");
                    fileexplorerfocus=false; downloadfocus=true;
                    btnfileexplorer.clearFocus();
                    btndownload.requestFocus();
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else if(btnhelp.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 3");
                    helpfocus=false;
                    btnhelp.clearFocus();
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                    if(btnfileexplorer.isEnabled()){
                        fileexplorerfocus=true;
                        btnfileexplorer.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        if(codeok){
                            downloadfocus=true;
                            btndownload.requestFocus();
                            reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            codefocus=true;
                            edtcode.requestFocus();
                            relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }
                    }
                }else if(btnprivacy.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 4");
                    privacyfocus=false;  helpfocus=true;
                    btnprivacy.clearFocus();
                    btnhelp.requestFocus();
                    relprivacy.setBackgroundResource(R.drawable.rounded_edittextg);
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else {
                    Log.d("debug KEYCODE_DPAD_UP", " 5");
                    if(downloadfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 1");
                        downloadfocus=false; codefocus=true;
                        btndownload.clearFocus();
                        edtcode.requestFocus();
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                        relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else if(fileexplorerfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 2");
                        fileexplorerfocus=false; downloadfocus=true;
                        btnfileexplorer.clearFocus();
                        btndownload.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else if(helpfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 3");
                        helpfocus=false;
                        btnhelp.clearFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                        if(btnfileexplorer.isEnabled()){
                            fileexplorerfocus=true;
                            btnfileexplorer.requestFocus();
                            relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            if(codeok){
                                downloadfocus=true;
                                btndownload.requestFocus();
                                reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                            }else {
                                codefocus=true;
                                edtcode.requestFocus();
                                relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                            }
                        }
                    }else if(privacyfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 4");
                        privacyfocus=false;  helpfocus=true;
                        btnprivacy.clearFocus();
                        btnhelp.requestFocus();
                        relprivacy.setBackgroundResource(R.drawable.rounded_edittextg);
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        codefocus=true;
                        edtcode.requestFocus();
                        relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }

                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:

                if(edtcode.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 1");
                    codefocus=false;
                    edtcode.clearFocus();
                    relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    if(codeok){
                        downloadfocus=true;
                        btndownload.requestFocus();
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        helpfocus=true;
                        btnhelp.requestFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }else if(btndownload.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 2");
                    downloadfocus=false;
                    btndownload.clearFocus();
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                    if(btnfileexplorer.isEnabled()){
                        fileexplorerfocus=true;
                        btnfileexplorer.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        helpfocus=true;
                        btnhelp.requestFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }else if(btnfileexplorer.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 3");
                    fileexplorerfocus=false;  helpfocus=true;
                    btnfileexplorer.clearFocus();
                    btnhelp.requestFocus();
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else if(btnhelp.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 4");
                    helpfocus=false; privacyfocus=true;
                    btnhelp.clearFocus();
                    btnprivacy.requestFocus();
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                    relprivacy.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else {
                    if(codefocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 1");
                        codefocus=false;
                        edtcode.clearFocus();
                        relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                        if(codeok){
                            downloadfocus=true;
                            btndownload.requestFocus();
                            reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            helpfocus=true;
                            btnhelp.requestFocus();
                            relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }
                    }else if(downloadfocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 2");
                        downloadfocus=false;
                        btndownload.clearFocus();
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                        if(btnfileexplorer.isEnabled()){
                            fileexplorerfocus=true;
                            btnfileexplorer.requestFocus();
                            relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            helpfocus=true;
                            btnhelp.requestFocus();
                            relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }
                    }else if(fileexplorerfocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 3");
                        fileexplorerfocus=false;  helpfocus=true;
                        btnfileexplorer.clearFocus();
                        btnhelp.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else if(helpfocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 4");
                        helpfocus=false; privacyfocus=true;
                        btnhelp.clearFocus();
                        btnprivacy.requestFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                        relprivacy.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
//                        privacyfocus=true;
//                        btnprivacy.requestFocus();
//                        relprivacy.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }

                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                if(edtcode.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 1");
                    codefocus=false;
                    edtcode.clearFocus();
                    relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    if(codeok){
                        downloadfocus=true;
                        btndownload.requestFocus();
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        helpfocus=true;
                        btnhelp.requestFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }else if(btndownload.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 2");
                    downloadfocus=false;
                    btndownload.clearFocus();
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                    if(btnfileexplorer.isEnabled()){
                        fileexplorerfocus=true;
                        btnfileexplorer.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        helpfocus=true;
                        btnhelp.requestFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }else if(btnfileexplorer.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 3");
                    fileexplorerfocus=false;  helpfocus=true;
                    btnfileexplorer.clearFocus();
                    btnhelp.requestFocus();
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else if(btnhelp.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_DOWN", " 4");
                    helpfocus=false; privacyfocus=true;
                    btnhelp.clearFocus();
                    btnprivacy.requestFocus();
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                    relprivacy.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else {
                    if(codefocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 1");
                        codefocus=false;
                        edtcode.clearFocus();
                        relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                        if(codeok){
                            downloadfocus=true;
                            btndownload.requestFocus();
                            reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            helpfocus=true;
                            btnhelp.requestFocus();
                            relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }
                    }else if(downloadfocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 2");
                        downloadfocus=false;
                        btndownload.clearFocus();
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                        if(btnfileexplorer.isEnabled()){
                            fileexplorerfocus=true;
                            btnfileexplorer.requestFocus();
                            relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            helpfocus=true;
                            btnhelp.requestFocus();
                            relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }
                    }else if(fileexplorerfocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 3");
                        fileexplorerfocus=false;  helpfocus=true;
                        btnfileexplorer.clearFocus();
                        btnhelp.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else if(helpfocus){
                        Log.d("debug KEYCODE_DPAD_DOWN", " 4");
                        helpfocus=false; privacyfocus=true;
                        btnhelp.clearFocus();
                        btnprivacy.requestFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                        relprivacy.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
//                        privacyfocus=true;
//                        btnprivacy.requestFocus();
//                        relprivacy.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }

                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:

                if(btndownload.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 1");
                    downloadfocus=false; codefocus=true;
                    btndownload.clearFocus();
                    edtcode.requestFocus();
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else if(btnfileexplorer.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 2");
                    fileexplorerfocus=false; downloadfocus=true;
                    btnfileexplorer.clearFocus();
                    btndownload.requestFocus();
                    relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                    reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else if(btnhelp.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 3");
                    helpfocus=false;
                    btnhelp.clearFocus();
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                    if(btnfileexplorer.isEnabled()){
                        fileexplorerfocus=true;
                        btnfileexplorer.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        if(codeok){
                            downloadfocus=true;
                            btndownload.requestFocus();
                            reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            codefocus=true;
                            edtcode.requestFocus();
                            relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }
                    }
                }else if(btnprivacy.hasFocus()){
                    Log.d("debug KEYCODE_DPAD_UP", " 4");
                    privacyfocus=false;  helpfocus=true;
                    btnprivacy.clearFocus();
                    btnhelp.requestFocus();
                    relprivacy.setBackgroundResource(R.drawable.rounded_edittextg);
                    relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                }else {
                    Log.d("debug KEYCODE_DPAD_UP", " 5");
                    if(downloadfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 1");
                        downloadfocus=false; codefocus=true;
                        btndownload.clearFocus();
                        edtcode.requestFocus();
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                        relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else if(fileexplorerfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 2");
                        fileexplorerfocus=false; downloadfocus=true;
                        btnfileexplorer.clearFocus();
                        btndownload.requestFocus();
                        relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextg);
                        reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else if(helpfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 3");
                        helpfocus=false;
                        btnhelp.clearFocus();
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextg);
                        if(btnfileexplorer.isEnabled()){
                            fileexplorerfocus=true;
                            btnfileexplorer.requestFocus();
                            relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                        }else {
                            if(codeok){
                                downloadfocus=true;
                                btndownload.requestFocus();
                                reldownload.setBackgroundResource(R.drawable.rounded_edittextgreen);
                            }else {
                                codefocus=true;
                                edtcode.requestFocus();
                                relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                            }
                        }
                    }else if(privacyfocus){
                        Log.d("debug KEYCODE_DPAD_UP", " 4");
                        privacyfocus=false;  helpfocus=true;
                        btnprivacy.clearFocus();
                        btnhelp.requestFocus();
                        relprivacy.setBackgroundResource(R.drawable.rounded_edittextg);
                        relhelp.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }else {
                        codefocus=true;
                        edtcode.requestFocus();
                        relcode.setBackgroundResource(R.drawable.rounded_edittextgreen);
                    }
                }

                break;
            case KeyEvent.KEYCODE_BACK:

                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                }else {
                    finish();
                }
                break;
            case KeyEvent.KEYCODE_ESCAPE:

//                break;
//                Log.d("OnKey", "key pressed!");
//                Toast.makeText(MainActivity.this, "key pressed!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }



    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            String filename = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String fileName =  new File(url.toString()).getName();
                if(fileName == null){
                    fileName="game1.apk";
                }
                if(fileName.contains("?")){
                    String[] separated = fileName.split("\\?");
                    fileName=separated[0];
                }
                if(!fileName.contains(".apk")){
                    fileName=fileName+".apk";
                }
                File filcheckname = new File(Environment.getExternalStorageDirectory() + "/Download/TvDownload/"+fileName);
                if(filcheckname.isDirectory()){
                    int rr=new Random().nextInt(100 - 1 + 1) + 1;
                    fileName=rr+""+fileName;
                }
                File f = new File(Environment.getExternalStorageDirectory() + "/Download/TvDownload/");
                if(f.isDirectory()) {
                }else {
                    f.mkdir();
                }
                File f2 = new File(Environment.getExternalStorageDirectory() + "/Download/TvDownload/",fileName);
                output = new FileOutputStream(f2.getAbsolutePath());

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
                Log.e("abcd ", result);
            }else {
                focus=2;
                btnfileexplorer.setEnabled(true);
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                relcode.setBackgroundResource(R.drawable.rounded_edittextg);
                reldownload.setBackgroundResource(R.drawable.rounded_edittextg);
                relfileexplorer.setBackgroundResource(R.drawable.rounded_edittextgreen);
                btnfileexplorer.requestFocus();

            }
        }
    }

    @SuppressLint("Range")
    public String getNameFromURI(@NonNull Context context, @NonNull Uri uri) {
        String result = null;
        Cursor c = null;
        try {
            c = context.getContentResolver().query(uri, null, null, null, null);
            c.moveToFirst();
            result = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
        catch (Exception e){
            // error occurs
        }
        finally {
            if(c != null){
                c.close();
            }
        }
        return result;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(downloadTask!=null) {
            downloadTask.cancel(true);
        }
    }


    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
//            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                    READ_STORAGE_PERMISSION_REQUEST_CODE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void selectDrawerItem(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.nav_facebook:
                Intent shareintent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/"));
                startActivity(shareintent);
                break;
            case R.id.nav_donate:
                Intent intent1=new Intent(MainActivity.this,DonateActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_contact:
                Intent intent=new Intent(MainActivity.this,ContactUsActivity.class);
                startActivity(intent);
                break;
            default:
//                Toast.makeText(this, "def", Toast.LENGTH_SHORT).show();
        }
//        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        }else {
            finish();
        }
    }
}