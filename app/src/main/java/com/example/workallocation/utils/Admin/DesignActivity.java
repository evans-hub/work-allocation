package com.example.workallocation.utils.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workallocation.R;
import com.example.workallocation.utils.Worker.Dashboard;
import com.google.firebase.auth.FirebaseAuth;

public class DesignActivity extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn,btn1;
    AlertDialog.Builder builds;
    FirebaseAuth mAuth;
    String subjects,names,deps,avail,id,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design4);
        btn = findViewById(R.id.button_p);
        name = findViewById(R.id.name_p);
        mAuth=FirebaseAuth.getInstance();
        urgency = findViewById(R.id.id_p);
        start = findViewById(R.id.availability_p);
        end = findViewById(R.id.email_p);
        dep = findViewById(R.id.department_p);
        status=findViewById(R.id.phone_p);


        String nn = getIntent().getStringExtra("name");
        String ss = getIntent().getStringExtra("id");
        String ee = getIntent().getStringExtra("phone");
        String stu = getIntent().getStringExtra("avail");
        String de = getIntent().getStringExtra("dep");
        String descc = getIntent().getStringExtra("emai");

        name.setText(nn);
        urgency.setText(ss);
        status.setText(ee);
        start.setText(stu);
        dep.setText(de);
        end.setText(descc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}