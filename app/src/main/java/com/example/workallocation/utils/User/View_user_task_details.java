package com.example.workallocation.utils.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workallocation.R;

public class View_user_task_details extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_task_details);
        btn = findViewById(R.id.pp_buttonsp);
        name = findViewById(R.id.pp_namesp);
        urgency = findViewById(R.id.pp_urgencysp);
        start = findViewById(R.id.pp_datesp);
        end = findViewById(R.id.pp_e_datesp);
        status = findViewById(R.id.pp_statussp);
        dep = findViewById(R.id.pp_departmentsp);
        to = findViewById(R.id.pp_descsp);
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
                Intent intent=new Intent(View_user_task_details.this, UserDashboard.class);
                startActivity(intent);
                finish();
            }
        });


    }
}