package com.example.workallocation.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.Entity.Model;
import com.example.workallocation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
private EditText nam,pass,phon,nation,ema;
Button btn;
FirebaseAuth mAuth;
DatabaseReference reference;
ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn=findViewById(R.id.btn_register);
        nam=findViewById(R.id.et_name);
        pass=findViewById(R.id.et_password);
        phon=findViewById(R.id.et_phone);
        nation=findViewById(R.id.et_id);
        ema=findViewById(R.id.et_email);
        mAuth=FirebaseAuth.getInstance();
        loading=new ProgressDialog(this);
        TextView textView=findViewById(R.id.swipeLeft);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration.this.startActivity(new Intent(Registration.this, LoginActivity.class));
                Registration.this.finish();
            }
        });
        reference= FirebaseDatabase.getInstance().getReference("users");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        loading.setMessage("Please wait...");
        loading.setTitle("Sign Up");
        loading.setCanceledOnTouchOutside(false);

        String name=nam.getText().toString().trim();
        String email=ema.getText().toString().trim();
        String password=pass.getText().toString().trim();
        String id=nation.getText().toString().trim();
        String phone=phon.getText().toString().trim();
        if (name.equalsIgnoreCase("")){
            nam.setError("Enter Name");
        }
        else if(email.equalsIgnoreCase("")){
            ema.setError("Enter Email");
        }
        else if(password.equalsIgnoreCase("")){
            pass.setError("Enter Password");
        }
        else if(id.equalsIgnoreCase("")){
            nation.setError("Enter National Id");
        }
        else if(phone.equalsIgnoreCase("")){
            phon.setError("Enter Phone number");
        }
        else
        {
            loading.show();
          mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()){
                     String bb= FirebaseAuth.getInstance().getCurrentUser().getUid();
                      Model model=new Model(name,email,phone,id,FirebaseAuth.getInstance().getCurrentUser().getUid());
                      reference.child(bb).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()){
                                  Toast.makeText(Registration.this, "Worker Registered Successfully", Toast.LENGTH_SHORT).show();

                                  Registration.this.startActivity(new Intent(Registration.this, LoginActivity.class));
                                  Registration.this.finish();
                                  loading.dismiss();
                              }
                              else{ loading.dismiss();
                                  String message=task.getException().toString();
                                  Toast.makeText(Registration.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                              }

                          }
                      });
                  }
                  else{
                      loading.dismiss();
                      String message=task.getException().toString();
                      Toast.makeText(Registration.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                  }

              }
          })  ;
        }
    }
}