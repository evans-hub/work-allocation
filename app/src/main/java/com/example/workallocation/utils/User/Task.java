package com.example.workallocation.utils.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.example.workallocation.utils.Admin.ViewActivity;
import com.example.workallocation.utils.Home;
import com.example.workallocation.utils.Worker.Dashboard;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Task extends AppCompatActivity {
    EditText title, description;
    CardView finance, others, inquire, admin, tech;
    Button btn, st, en;
    TextView tt;
    ImageView upload;
    Uri imageuri = null;
    ImageView back, q, w, e, r, t;
    DatabaseReference reference, re;
    StringBuilder sb;
    String day, dateno, month;
    int n = 5;
    String id;
    FirebaseAuth mAuth;
    AlertDialog.Builder builds;
    TextView uploads,textupload;
    String myurl,filename;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        String nn = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child("client");
        reference.child(nn).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = snapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        uploads=findViewById(R.id.uploading);
        textupload=findViewById(R.id.uploaded);
        uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });
        q = findViewById(R.id.q1);
        w = findViewById(R.id.q2);
        e = findViewById(R.id.q3);
        r = findViewById(R.id.q4);
        t = findViewById(R.id.q5);
        title = findViewById(R.id.l_title);
        description = findViewById(R.id.l_description);
        back = findViewById(R.id.back_arrow);
        finance = findViewById(R.id.l_finance);
        others = findViewById(R.id.l_others);
        inquire = findViewById(R.id.l_inquiries);
        admin = findViewById(R.id.l_admin);
        tech = findViewById(R.id.l_tech);
        btn = findViewById(R.id.btn_task);
        tt = findViewById(R.id.text);
        en = findViewById(R.id.l_end);
        st = findViewById(R.id.l_start);
        sb = new StringBuilder(n);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task.this, Dashboard.class);
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

        this.builds = new AlertDialog.Builder(this);
        this.builds.setMessage("Tasks").setTitle("Checking Dates");
        this.builds.setMessage("Start date cannot be greater").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                dialog.dismiss();
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("alltasks");
        re = FirebaseDatabase.getInstance().getReference("tasks");
        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("license");
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
                tt.setText("headquarters");
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
                tt.setText("ict");
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
                tt.setText("education");
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
                tt.setText("trade");
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
                try {
                    createTask();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
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

    private void createTask() throws ParseException {


        String tasktitle = title.getText().toString().trim();
        String taskdesc = description.getText().toString().trim();
        String category = tt.getText().toString().trim();
        String start = st.getText().toString();
        String end = en.getText().toString();
        EditText nn = findViewById(R.id.s_description);
        String s_description = nn.getText().toString().trim();
        Date dat = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
        String date = String.valueOf(System.currentTimeMillis());

        String taskId = sb.toString();
        SimpleDateFormat sdff = new SimpleDateFormat("dd-MM-yyyy");
        Date strDate = sdff.parse(start);
        Date endDate = sdff.parse(end);

        if (endDate.before(strDate)) {
            AlertDialog alert = Task.this.builds.create();
            alert.setTitle("Invalid Dates");
            alert.show();
        } else if (tasktitle.equalsIgnoreCase("")) {
            title.setError("Enter Title");
        } else if (taskdesc.equalsIgnoreCase("")) {
            description.setError("Enter Description");
        } else if (start.equalsIgnoreCase("")) {
            st.setError("Enter Start Date");
        } else if (end.equalsIgnoreCase("")) {
            en.setError("Enter Due Date");
        } else if (s_description.equalsIgnoreCase("")) {
            nn.setError("Enter Description");
        } else if (taskId.equalsIgnoreCase("")) {
            Toast.makeText(this, "Invalid task id", Toast.LENGTH_SHORT).show();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                day = LocalDate.now().getDayOfWeek().toString();
                month = LocalDate.now().getMonth().toString();
                dateno = String.valueOf(LocalDate.now().getDayOfMonth());
            }
            String assign = "unassigned";
            String status = "unassigned";
            String userid=mAuth.getCurrentUser().getUid();
            TaskModel model = new TaskModel(tasktitle, taskdesc, category, start, end, date, taskId, s_description, assign,myurl,status,userid);
            reference.child(taskId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid()).child(taskId);
                    ref.setValue(model);
                    Toast.makeText(Task.this, "Task created successfully", Toast.LENGTH_SHORT).show();

                    re.child(taskId).setValue(model);
                    Intent intent = new Intent(Task.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
    ProgressDialog dialog;
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
                        filename = uri.getLastPathSegment();
                        myurl = uri.toString();
                        textupload.setVisibility(View.VISIBLE);
                        textupload.setText(filename);
                        uploads.setText("File Uploaded");
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Task.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}