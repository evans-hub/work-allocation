package com.example.workallocation.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.Worker.Dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Design2 extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn,btn1;
    AlertDialog.Builder builds;
    FirebaseAuth mAuth;
    String subjects,names,deps,avail,id,phone,email;


    @Override
    protected void onStart() {
        super.onStart();
        String stu = getIntent().getStringExtra("id");
        DatabaseReference referencesfgf =  FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
        referencesfgf.child(stu).child("assigned_to").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects=snapshot.getValue(String.class);
                if (subjects.equalsIgnoreCase("accepted")){
                    btn.setVisibility(View.GONE);
                    btn1.setVisibility(View.VISIBLE);
                }
                else if(subjects.equalsIgnoreCase("Assigned")){
                    btn.setText("Accept");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design2);
        btn = findViewById(R.id.pp_button);
        btn1 = findViewById(R.id.pppp_button);
        name = findViewById(R.id.pp_name);
        mAuth=FirebaseAuth.getInstance();
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
        String stu = getIntent().getStringExtra("id");
        String de = getIntent().getStringExtra("dep");
        String descc = getIntent().getStringExtra("desc");

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
                String s1=getSharedPreferences("MySharedPref",0).getString("id_number","");
                DatabaseReference referencesfgf =  FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
                referencesfgf.child(stu).child("status").setValue("accepted");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("available").child(de).child(s1);
                ref.removeValue();
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("tasks").child(stu);
                DatabaseReference data=FirebaseDatabase.getInstance().getReference("alltasks").child(stu);
                data.child("status").setValue("accepted");
                reff.removeValue();
                AlertDialog alert = Design2.this.builds.create();
                alert.setTitle("Accepted Task");
                alert.show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=getSharedPreferences("MySharedPref",0).getString("id_number","");
                DatabaseReference db=FirebaseDatabase.getInstance().getReference("workers");
                db.child(s1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        names=snapshot.child("name").getValue(String.class);
                      deps=snapshot.child("dep").getValue(String.class);
                         email=snapshot.child("email").getValue(String.class);
                         phone=snapshot.child("phone").getValue(String.class);
                        avail=snapshot.child("availability").getValue(String.class);
                         id=snapshot.child("id").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference referencesfgf =  FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
                referencesfgf.child(stu).child("assigned_to").setValue("Completed");
                DatabaseReference data=FirebaseDatabase.getInstance().getReference("alltasks").child(stu);
                data.setValue("Completed");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("available").child(de).child(s1);
                workModel workModel=new workModel(names,email,phone,id,deps,avail);
                ref.setValue(workModel);
                Intent intent=new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        this.builds = new AlertDialog.Builder(this);
        this.builds.setMessage("Tasks").setTitle("Task Accepted");
        this.builds.setMessage("You have accepted the task successfully").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
              dialog.dismiss();
              Intent intent=new Intent(getApplicationContext(),Dashboard.class);
              startActivity(intent);
              finish();
            }
        });
    }
}