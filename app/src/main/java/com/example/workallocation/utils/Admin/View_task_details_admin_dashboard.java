package com.example.workallocation.utils.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workallocation.R;
import com.example.workallocation.utils.Admin.AdminDashboard;
import com.google.firebase.auth.FirebaseAuth;

public class View_task_details_admin_dashboard extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn,btn1;
    FirebaseAuth mAuth;
    String subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_details_admin_dashboard);
        btn = findViewById(R.id.cpp_buttons);
        name = findViewById(R.id.pp_names);
        mAuth=FirebaseAuth.getInstance();
        urgency = findViewById(R.id.pp_urgencys);
        start = findViewById(R.id.pp_dates);
        end = findViewById(R.id.pp_e_dates);
        status = findViewById(R.id.pp_statuss);
        dep = findViewById(R.id.pp_departments);
        to = findViewById(R.id.pp_descs);
        to.setMovementMethod(new ScrollingMovementMethod());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
                finish();
            }
        });


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
    }
}