package com.example.workallocation.utils.Worker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.Entity.Uploads;
import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AcceptingDesign extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status, shows;
    Button btn, btn1, btn2, btn3;
    AlertDialog.Builder builds, builder;
    FirebaseAuth mAuth;
    Uri imageuri = null;
    String subjects, names, deps, avail, id, phone, email, myurl, stu;
    //test
private static final int PERMISSION_STORAGE_CODE = 1000;
String fil,uid;


    @Override
    protected void onStart() {
        super.onStart();
        String stu = getIntent().getStringExtra("id");
        DatabaseReference referencesfgf = FirebaseDatabase.getInstance().getReference("alltasks");
        referencesfgf.child(stu).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects = snapshot.getValue(String.class);
                if (subjects.equalsIgnoreCase("accepted")) {
                    btn.setVisibility(View.GONE);
                    btn1.setVisibility(View.VISIBLE);
                } else if (subjects.equalsIgnoreCase("Assigned")) {
                    btn.setText("Accept");
                } else if (subjects.equalsIgnoreCase("uploaded")) {
                    btn.setText("Uploaded");
                } else if (subjects.equalsIgnoreCase("Completed")) {
                   // btn2.setText("Upload Files");
                    btn2.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fil = getIntent().getStringExtra("file");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepting_design);
        btn = findViewById(R.id.pp_button);
        shows = findViewById(R.id.show);
        btn1 = findViewById(R.id.pppp_button);
        btn2 = findViewById(R.id.pppp_buttons);
        name = findViewById(R.id.pp_name);
        mAuth = FirebaseAuth.getInstance();
        urgency = findViewById(R.id.pp_urgency);
        start = findViewById(R.id.pp_date);
        end = findViewById(R.id.pp_e_date);
        status = findViewById(R.id.pp_status);
        dep = findViewById(R.id.pp_department);
        to = findViewById(R.id.pp_desc);
        to.setMovementMethod(new ScrollingMovementMethod());


        String nn = getIntent().getStringExtra("name");
        String uu = "critical";
        String ss = getIntent().getStringExtra("start");
        String ee = getIntent().getStringExtra("end");
        stu = getIntent().getStringExtra("id");
        String de = getIntent().getStringExtra("dep");
        String descc = getIntent().getStringExtra("desc");
        fil = getIntent().getStringExtra("file");
         uid = getIntent().getStringExtra("uid");

        name.setText(nn);
        urgency.setText(uu);
        start.setText(ss);
        end.setText(ee);
        status.setText(stu);
        dep.setText(de);
        to.setText(descc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = getSharedPreferences("MySharedPref", 0).getString("id_number", "");
                DatabaseReference referencesfgf = FirebaseDatabase.getInstance().getReference("alltasks");
                referencesfgf.child(stu).child("status").setValue("accepted");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("available").child(de).child(s1);
                ref.removeValue();
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("tasks").child(stu);
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("alltasks").child(stu);
                data.child("status").setValue("accepted");
                reff.removeValue();
                //
               /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        Toast.makeText(AcceptingDesign.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    }
                    else{
//start
                       // startDownloading();
                    }
                }
                else {
                    //start
                   // startDownloading();
                }*/
                //
                AlertDialog alert = AcceptingDesign.this.builds.create();
                alert.setTitle("Accepted Task");
                alert.show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = getSharedPreferences("MySharedPref", 0).getString("id_number", "");
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("workers");
                db.child(s1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        names = snapshot.child("name").getValue(String.class);
                        deps = snapshot.child("dep").getValue(String.class);
                        email = snapshot.child("email").getValue(String.class);
                        phone = snapshot.child("phone").getValue(String.class);
                        avail = snapshot.child("availability").getValue(String.class);
                        id = snapshot.child("id").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference referencesfgf = FirebaseDatabase.getInstance().getReference("alltasks");
                referencesfgf.child(stu).child("assigned_to").setValue("Completed");
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("alltasks").child(stu);
                data.child("status").setValue("Completed");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("available").child(de).child(s1);
                workModel workModel = new workModel(names, email, phone, id, deps, avail);
                ref.setValue(workModel);
                Intent intent=new Intent(getApplicationContext(), WorkerDashboard.class);
                startActivity(intent);
            }
        });
      /*  btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = getSharedPreferences("MySharedPref", 0).getString("id_number", "");
                AlertDialog alert = AcceptingDesign.this.builder.create();
                alert.setTitle("Upload Files");
                alert.show();

            }
        });*/


     /*   this.builder = new AlertDialog.Builder(this);
        this.builder.setMessage("Tasks").setTitle("Task Complete");
        this.builder.setMessage("Upload Files").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                dialog.dismiss();
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getApplicationContext(), WorkerDashboard.class);
                startActivity(intent);
                finish();
            }
        });*/
        this.builds = new AlertDialog.Builder(this);
        this.builds.setTitle("Task Accepted");
        this.builds.setMessage("Task Accepted successfully").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                dialog.dismiss();
                Intent intent=new Intent(getApplicationContext(), WorkerDashboard.class);
                startActivity(intent);
                finish();
              /*  Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);*/
            }
        });
    }

   /* private void startDownloading() {
        String url=fil;
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading file...");

        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+ System.currentTimeMillis());
        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }*/

    ProgressDialog dialog;
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloading();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;


            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");

            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull com.google.android.gms.tasks.Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();

                        myurl = uri.toString();
                        Uploads up = new Uploads(stu, myurl);
                        DatabaseReference ta = FirebaseDatabase.getInstance().getReference("uploads");
                        ta.child(stu).setValue(up).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    *//*DatabaseReference data = FirebaseDatabase.getInstance().getReference("alltasks").child(stu);
                                    data.child("status").setValue("Submitted");
                                    DatabaseReference datas = FirebaseDatabase.getInstance().getReference("usertask").child(uid).child(stu);
                                    datas.child("file").setValue(myurl);*//*
                                    shows.setVisibility(View.VISIBLE);
                                    shows.setText(filepath.getName());
                                    Intent intent = new Intent(getApplicationContext(), AcceptingDesign.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AcceptingDesign.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/

}