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

import com.example.workallocation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
EditText em,pp;
Button btn;
ProgressDialog loading;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=findViewById(R.id.btn_login);
        em =findViewById(R.id.l_email);
        pp=findViewById(R.id.l_password);
        loading=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        TextView textView=findViewById(R.id.swipeLeftee);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, Registration.class));
                LoginActivity.this.finish();

            }
        });
        if (mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        loading.setMessage("Please Wait...");
        loading.setTitle("Sign In");
        loading.setCanceledOnTouchOutside(false);

        String email=em.getText().toString().trim();
        String password=pp.getText().toString().trim();
        if(email.equalsIgnoreCase("")){
            em.setError("Enter Email");
        }
        else if(password.equalsIgnoreCase("")){
            pp.setError("Enter Password");
        }
        else{
            loading.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Intent intent=new Intent(LoginActivity.this,SplashScreen.class);
                   startActivity(intent);
                   loading.dismiss();
               }
               else {
                   loading.dismiss();
                   Toast.makeText(LoginActivity.this, "Error:"+task.getException().toString(), Toast.LENGTH_SHORT).show();
               }
                }
            });
        }
    }
}