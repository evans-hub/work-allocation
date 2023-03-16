package com.example.workallocation.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.R;
import com.example.workallocation.utils.Admin.AdminDashboard;
import com.example.workallocation.utils.Admin.Workers;
import com.example.workallocation.utils.User.UserDashboard;
import com.example.workallocation.utils.Worker.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText em,pp;
Button btn;
ProgressDialog loading;
FirebaseAuth mAuth;
Spinner spin;
String user;
String id,bn;
TextView vb;
String dep[]={"Admin","Client","Worker"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=findViewById(R.id.btn_login);
        em =findViewById(R.id.l_email);
        pp=findViewById(R.id.l_password);
        loading=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        spin=findViewById(R.id.spinnerr);
        vb=findViewById(R.id.cl);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad= new ArrayAdapter(this,android.R.layout.simple_spinner_item,dep);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);
        TextView textView=findViewById(R.id.swipeLeftee);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, Registration.class));
                LoginActivity.this.finish();

            }
        });
        if (mAuth.getCurrentUser()!=null){
            String emm=mAuth.getCurrentUser().getEmail();
            if (emm.contains("admin")){
                Intent intent=new Intent(LoginActivity.this, AdminDashboard.class);
                startActivity(intent);
                loading.dismiss();
                finish();
            }
           else if (emm.contains("worker")){
                Intent intent=new Intent(LoginActivity.this, UserDashboard.class);
                startActivity(intent);
                loading.dismiss();
                finish();
            }
           else {
                Intent intent=new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }

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

        String email=em.getText().toString().trim().toLowerCase();
        String password=pp.getText().toString().trim();
        if(email.equalsIgnoreCase("")){
            em.setError("Enter Email");
        }
        else if(password.equalsIgnoreCase("")){
            pp.setError("Enter Password");
        }
        else{
            if (user.equalsIgnoreCase("admin")){
                if (!email.contains("admin")){
                    em.setError("Invalid Admin Email");
                }
                else{
                    loading.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mAuth=FirebaseAuth.getInstance();

                                if (user.equalsIgnoreCase("admin")){
                                    Intent intent=new Intent(LoginActivity.this,AdminDashboard.class);
                                    startActivity(intent);
                                    loading.dismiss();
                                    finish();
                                }
                                else if(user.equalsIgnoreCase("worker")){
                                    Intent intent=new Intent(LoginActivity.this, UserDashboard.class);
                                    startActivity(intent);
                                    loading.dismiss();
                                    finish();
                                }
                                else {
                                    Intent intent=new Intent(LoginActivity.this, Dashboard.class);
                                    startActivity(intent);
                                    loading.dismiss();
                                    finish();

                                }
                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(LoginActivity.this, "Error:"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            else if (user.equalsIgnoreCase("worker")){
                if (!email.contains("worker")){
                    em.setError("Invalid Worker Email");
                }
                else{
                    loading.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mAuth=FirebaseAuth.getInstance();
                                if (user.equalsIgnoreCase("admin")){
                                    SharedPreferences.Editor myEdit = LoginActivity.this.getSharedPreferences("MySharedPref", 0).edit();
                                    myEdit.putString("emailfromlogin", email);
                                    myEdit.putString("passwordfromlogin", password);
                                    myEdit.commit();
                                    Intent intent=new Intent(LoginActivity.this,AdminDashboard.class);
                                    startActivity(intent);
                                    loading.dismiss();
                                    finish();
                                }
                                else if(user.equalsIgnoreCase("worker")){
                                    Intent intent=new Intent(LoginActivity.this, UserDashboard.class);
                                    startActivity(intent);
                                    loading.dismiss();
                                    finish();
                                }
                                else {
                                    Intent intent=new Intent(LoginActivity.this, Dashboard.class);
                                    startActivity(intent);
                                    loading.dismiss();
                                    finish();

                                }
                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(LoginActivity.this, "Error:"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            else {

                loading.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth = FirebaseAuth.getInstance();
                            if (user.equalsIgnoreCase("client")) {
                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);


                                startActivity(intent);
                                finish();
                                loading.dismiss();
                                finish();
                            } else if (user.equalsIgnoreCase("admin")) {
                                Intent intent = new Intent(LoginActivity.this, AdminDashboard.class);
                                startActivity(intent);
                                loading.dismiss();
                                finish();
                            } else if (user.equalsIgnoreCase("worker")) {
                                Intent intent = new Intent(LoginActivity.this, UserDashboard.class);
                                startActivity(intent);
                                loading.dismiss();
                                finish();
                            }
                        } else {
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this, "Error:" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        user=dep[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}