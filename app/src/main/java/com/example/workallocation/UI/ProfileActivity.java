package com.example.workallocation.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workallocation.R;
import com.example.workallocation.Entrypoint.LoginActivity;
import com.example.workallocation.utils.User.UserDashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
TextView email,phone,id,user,name;
DatabaseReference reference;
FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        String emaill=mAuth.getCurrentUser().getEmail();
        if (emaill.contains("admin")){
            user.setText("Admin");
        }
        else if (emaill.contains("worker")){
            user.setText("Worker");
        }
        else{
            user.setText("Client");
        }
        reference= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                email.setText(snapshot.child("email").getValue(String.class));
                id.setText(snapshot.child("id").getValue(String.class));
                name.setText(snapshot.child("name").getValue(String.class));
                phone.setText(snapshot.child("phone").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email=findViewById(R.id.profile_emailaddress);
        phone=findViewById(R.id.profile_phonenumber);
        id=findViewById(R.id.profile);
        name=findViewById(R.id.profile_name);
        user=findViewById(R.id.profile_client);
        ImageView im=findViewById(R.id.back_arrows);
        mAuth=FirebaseAuth.getInstance();
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this, UserDashboard.class);
                startActivity(intent);
                finish();
            }
        });
        Button btn=findViewById(R.id.logoutuser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                ProfileActivity.this.finish();
            }
        });

    }
}