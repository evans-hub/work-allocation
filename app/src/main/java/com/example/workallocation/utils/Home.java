package com.example.workallocation.utils;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.workallocation.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Home extends AppCompatActivity {

    ImageView upload;
    Uri imageuri = null;
    private static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        upload = findViewById(R.id.uploadpdf);

        // After Clicking on this we will be
        // redirected to choose pdf
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        Toast.makeText(Home.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    }
                    else{
//start
                        startDownloading();
                    }
                }
                else {
                    //start
                    startDownloading();
                }
            }
        });
    }

    private void startDownloading() {
        String url="https://firebasestorage.googleapis.com/v0/b/work-allocation-efb68.appspot.com/o/1673331960487.pdf?alt=media&token=5e08da35-605e-4b3a-92ce-5b8e570415f3";
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading file...");

        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+ System.currentTimeMillis());
        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }



}
