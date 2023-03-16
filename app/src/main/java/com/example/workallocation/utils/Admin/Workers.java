package com.example.workallocation.utils.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.ProfileActivity;
import com.example.workallocation.utils.SplashScreen;
import com.example.workallocation.utils.Worker.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Workers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nam, pass, phon, nation, ema;
    Button btn;
    FirebaseAuth mAuth;
    DatabaseReference reference,reference2, refer, ref,referal;
    ProgressDialog loading;
    String department;
    String[] depart = {"License", "Ict", "Trade", "Education", "Headquarters"};

  /*  @Override
    protected void onStart() {
        super.onStart();
        String valid_until = "31/12/2022";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (new Date().after(strDate)) {
            Toast.makeText(this, "best", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "worst", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workers);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        btn = findViewById(R.id.btn_work);
        nam = findViewById(R.id.w_name);
        phon = findViewById(R.id.w_phone);
        nation = findViewById(R.id.w_id);
        ema = findViewById(R.id.w_email);
        pass = findViewById(R.id.w_pass);
        mAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(this);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, depart);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);
        reference = FirebaseDatabase.getInstance().getReference("workers");
        reference2 = FirebaseDatabase.getInstance().getReference("users");
        refer = FirebaseDatabase.getInstance().getReference("available");
        ref = FirebaseDatabase.getInstance().getReference("everyworker");
        referal = FirebaseDatabase.getInstance().getReference("workemail");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), ViewActivity.class));
                        finish();
                        return false;
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        finish();
                        return false;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        return false;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), AllWorkers.class));
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    private void register() {
        loading.setMessage("Please wait...");
        loading.setTitle("Registering Worker");
        loading.setCanceledOnTouchOutside(false);

        String name = nam.getText().toString().trim();
        String email = ema.getText().toString().trim();
        String id = nation.getText().toString().trim();
        String phone = phon.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (name.equalsIgnoreCase("")) {
            nam.setError("Enter Name");
        } else if (email.equalsIgnoreCase("")) {
            ema.setError("Enter Email");
        } else if (!email.contains("worker")) {
            ema.setError("Email must contain worker");
        } else if (id.equalsIgnoreCase("")) {
            nation.setError("Enter National Id");
        } else if (phone.equalsIgnoreCase("")) {
            phon.setError("Enter Phone number");
        } else {
            loading.show();
            String availability = "available";

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uid=mAuth.getCurrentUser().getUid();
                        workModel model = new workModel(name, email, phone, id, department, availability,uid);
                        reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    refer.child(department).child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                referal.child(mAuth.getCurrentUser().getUid()).setValue(model);
                                                ref.child(id).setValue(model);
                                                reference2.child(uid).setValue(model);
                                                loading.dismiss();
                                                Toast.makeText(Workers.this, "Worker Registered Successfully", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(Workers.this, "Error:" + task.getException().toString(), Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });

                                    Intent intent = new Intent(Workers.this, AdminDashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Workers.this, "Error:" + task.getException().toString(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    } else {
                        Toast.makeText(Workers.this, "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        department = depart[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}