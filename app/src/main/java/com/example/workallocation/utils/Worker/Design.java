package com.example.workallocation.utils.Worker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workallocation.R;
import com.example.workallocation.utils.Admin.Available;

public class Design extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        btn = findViewById(R.id.p_button);
        name = findViewById(R.id.p_name);
        urgency = findViewById(R.id.p_urgency);
        start = findViewById(R.id.p_date);
        end = findViewById(R.id.p_e_date);
        status = findViewById(R.id.p_status);
        dep = findViewById(R.id.p_department);
        to = findViewById(R.id.p_desc);
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
                Intent intent=new Intent(Design.this, Available.class);
                intent.putExtra("id",stu);
                intent.putExtra("dep",de);
                startActivity(intent);
            }
        });


    }
}