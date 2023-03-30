package com.sttech.tvdownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvEmpty;
    ArrayList<File> arrayList=new ArrayList<>();
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){}

        // assign variable
        recyclerView=findViewById(R.id.recycler_view);
        tvEmpty=findViewById(R.id.tv_empty);

        // add values in arraylist
        listFiles();
        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        adapter=new MainAdapter(this,arrayList,tvEmpty);

        // set adapter
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listFiles() {

//        File f = new File(Environment.getExternalStorageDirectory() + "/Download/TvDownload/");
        File fl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/TvDownload");
        File[] files = fl.listFiles();
        Log.i("File", String.valueOf(files.length));

        for (int i = 0; i < files.length; i++) {
            Log.i("Files", files[i].getName());
            arrayList.add(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/TvDownload/"+files[i].getName()));
        }
    }

}