package com.sttech.tvdownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileExplorerActivity extends AppCompatActivity {

    List<File> fileList;
    RecyclerView reccode;
    DataAdapter1 carDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);

        fileList=new ArrayList<>();
        reccode=findViewById(R.id.reccode);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        reccode.setLayoutManager(gridLayoutManager);

        listFiles();

        carDataAdapter = new DataAdapter1(FileExplorerActivity.this, fileList);
        carDataAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                showDialog(FileExplorerActivity.this,i);

//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FileExplorerActivity.this);
//                alertDialog.setTitle("Install");
//                alertDialog.setMessage("Are you want to install this app?");
//                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                alertDialog.setNegativeButton("Install", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            Uri uri = FileProvider.getUriForFile(FileExplorerActivity.this, BuildConfig.APPLICATION_ID + ".provider", fileList.get(i));
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
////                        }else {
////
////                        }
//
//                    }
//                });
//
//                AlertDialog dialog = alertDialog.create();
//                dialog.show();
            }
        });
        reccode.setAdapter(carDataAdapter);
        carDataAdapter.notifyDataSetChanged();

    }

    private void listFiles() {

//        File f = new File(Environment.getExternalStorageDirectory() + "/Download/TvDownload/");
        File fl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/TvDownload");
        File[] files = fl.listFiles();
        Log.i("File", String.valueOf(files.length));

        for (int i = 0; i < files.length; i++) {
            Log.i("Files", files[i].getName());
            fileList.add(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/TvDownload/"+files[i].getName()));
        }
    }


    public void showDialog(Activity activity, int i){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        Button btn_install = (Button) dialog.findViewById(R.id.btn_install);
        Button btn_delete = (Button) dialog.findViewById(R.id.btn_delete);
        Button btn_close = (Button) dialog.findViewById(R.id.btn_close);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDeleteDialog(i);
//                fileList.get(i).delete();
//                fileList.remove(i);
//                carDataAdapter.notifyDataSetChanged();
//                dialog.dismiss();
            }
        });

        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = FileProvider.getUriForFile(FileExplorerActivity.this, BuildConfig.APPLICATION_ID + ".provider", fileList.get(i));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void ShowDeleteDialog(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                fileList.get(i).delete();
                fileList.remove(i);
                carDataAdapter.notifyDataSetChanged();
                dialog.dismiss();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


}