package com.example.workallocation.utils.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.Adapters.AdminAdapter;
import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity {
ImageView img;
TextView task,user,comp,pend;
FirebaseAuth mAuth;
AdminAdapter adapter;
DatabaseReference references;
RecyclerView recyclerView;
    ArrayList<TaskModel> list,listing;
    ProgressDialog loading;

    @Override
    protected void onStart() {
        super.onStart();
        String nn=mAuth.getCurrentUser().getEmail();
        user.setText(nn);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        task=findViewById(R.id.available_tasks);
        user=findViewById(R.id.usernames);
        img=findViewById(R.id.profiles);
        TextView tt=findViewById(R.id.viewss);
        mAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.reccc);
        loading=new ProgressDialog(this);
        TextView textView=findViewById(R.id.view);
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AllWorkers.class);
                startActivity(intent);

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ViewActivity.class);
                startActivity(intent);

            }
        });

        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        listing = new ArrayList<>();
        adapter = new AdminAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    navigationView.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || (dy < 0 && navigationView.isShown())) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });
        this.loading.show();
        references=FirebaseDatabase.getInstance().getReference("alltasks");
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((TaskModel) dataSnapshot.getValue(TaskModel.class));
                }
                adapter.notifyDataSetChanged();
                if (AdminDashboard.this.list.size() == 0) {
                    Toast.makeText(AdminDashboard.this, "No tasks", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
                int total = 0;
                for (int i = 0; i < AdminDashboard.this.list.size(); i++) {
                    total++;
                    task.setText(total+"Tasks");
                }
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(AdminDashboard.this, "Error", Toast.LENGTH_SHORT).show();
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
}