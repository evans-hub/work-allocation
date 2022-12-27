package com.example.workallocation.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Task extends AppCompatActivity {
EditText title,description;
CardView finance,others,inquire,admin,tech;
Button btn,st,en;
TextView tt;
ImageView back,q,w,e,r,t;
DatabaseReference reference,re;
    StringBuilder sb;
    String day,dateno,month;
    int n=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        q=findViewById(R.id.q1);
        w=findViewById(R.id.q2);
        e=findViewById(R.id.q3);
        r=findViewById(R.id.q4);
        t=findViewById(R.id.q5);
        title=findViewById(R.id.l_title);
        description=findViewById(R.id.l_description);
        back=findViewById(R.id.back_arrow);
        finance=findViewById(R.id.l_finance);
        others=findViewById(R.id.l_others);
        inquire=findViewById(R.id.l_inquiries);
        admin=findViewById(R.id.l_admin);
        tech=findViewById(R.id.l_tech);
        btn=findViewById(R.id.btn_task);
        tt=findViewById(R.id.text);
        en=findViewById(R.id.l_end);
        st=findViewById(R.id.l_start);
        sb = new StringBuilder(n);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Task.this,SplashScreen.class);
                startActivity(intent);
            }
        });
randomString();
st.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(Task.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Task.this.st.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, c.get(1), c.get(2), c.get(5)).show();
    }
});
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(Task.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Task.this.en.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, c.get(1), c.get(2), c.get(5)).show();
            }
        });




        reference= FirebaseDatabase.getInstance().getReference("alltasks");
        re= FirebaseDatabase.getInstance().getReference("tasks");
        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("finance");
                q.setVisibility(View.VISIBLE);
                w.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
                r.setVisibility(View.GONE);
                t.setVisibility(View.GONE);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("others");
                q.setVisibility(View.GONE);
                w.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
                r.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
            }
        });
        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("inquiries");
                q.setVisibility(View.GONE);
                w.setVisibility(View.VISIBLE);
                e.setVisibility(View.GONE);
                r.setVisibility(View.GONE);
                t.setVisibility(View.GONE);
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("technical");
                q.setVisibility(View.GONE);
                w.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
                r.setVisibility(View.VISIBLE);
                t.setVisibility(View.GONE);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("admin");
                q.setVisibility(View.GONE);
                w.setVisibility(View.GONE);
                e.setVisibility(View.VISIBLE);
                r.setVisibility(View.GONE);
                t.setVisibility(View.GONE);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });

    }

    private void randomString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
    }

    private void createTask() {


        String tasktitle=title.getText().toString().trim();
        String taskdesc=description.getText().toString().trim();
        String category=tt.getText().toString().trim();
        String start=st.getText().toString();
        String end=en.getText().toString();
        EditText nn=findViewById(R.id.s_description);
        String s_description=nn.getText().toString().trim();
        Date dat=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
        String date=sdf.format(dat).toString();
       String taskId=sb.toString();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            day= LocalDate.now().getDayOfWeek().toString();
            month= LocalDate.now().getMonth().toString();
            dateno= String.valueOf(LocalDate.now().getDayOfMonth());
        }
String assign="unassigned";
        TaskModel model=new TaskModel(tasktitle,taskdesc,category,start,end,date,taskId,s_description,assign);
        reference.child(taskId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                Toast.makeText(Task.this, "Task created successfully", Toast.LENGTH_SHORT).show();
                re.child(taskId).setValue(model);
                Intent intent=new Intent(Task.this,ViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}