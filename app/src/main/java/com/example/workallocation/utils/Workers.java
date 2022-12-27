package com.example.workallocation.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Workers extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText nam,pass,phon,nation,ema;
    Button btn;
    FirebaseAuth mAuth;
    DatabaseReference reference,refer;
    ProgressDialog loading;
    String department;
    String[] depart={"Finance","Inquiries","Technical","Admin","Others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workers);
        Spinner spin=(Spinner)findViewById(R.id.spinner);
        btn=findViewById(R.id.btn_work);
        nam=findViewById(R.id.w_name);
        phon=findViewById(R.id.w_phone);
        nation=findViewById(R.id.w_id);
        ema=findViewById(R.id.w_email);
        mAuth=FirebaseAuth.getInstance();
        loading=new ProgressDialog(this);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad= new ArrayAdapter(this,android.R.layout.simple_spinner_item,depart);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);
        reference= FirebaseDatabase.getInstance().getReference("workers");
        refer= FirebaseDatabase.getInstance().getReference("available");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        loading.setMessage("Please wait...");
        loading.setTitle("Registering Worker");
        loading.setCanceledOnTouchOutside(false);

        String name=nam.getText().toString().trim();
        String email=ema.getText().toString().trim();
        String id=nation.getText().toString().trim();
        String phone=phon.getText().toString().trim();
        if (name.equalsIgnoreCase("")){
            nam.setError("Enter Name");
        }
        else if(email.equalsIgnoreCase("")){
            ema.setError("Enter Email");
        }
        else if(id.equalsIgnoreCase("")){
            nation.setError("Enter National Id");
        }
        else if(phone.equalsIgnoreCase("")){
            phon.setError("Enter Phone number");
        }
        else{
            loading.show();
            String availability="available";
            workModel model=new workModel(name,email,phone,id,department,availability);
            reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        refer.child(department).child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Workers.this, "Worker Registered Successfully", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(Workers.this, "Error:"+task.getException().toString(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                        Intent intent=new Intent(Workers.this,SplashScreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Workers.this, "Error:"+task.getException().toString(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
department=depart[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}