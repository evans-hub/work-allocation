package com.example.workallocation.utils.Worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.Adapters.WorkerDashboardAdapter;
import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.example.workallocation.UI.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerDashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    WorkerDashboardAdapter adapter;
    String uid,idnumber;
    TextView textView;

    @Override
    protected void onStart() {
        super.onStart();
        uid=mAuth.getCurrentUser().getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("workemail");
        reference.child(uid).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idnumber=snapshot.getValue(String.class);
                textView.setText(idnumber);
                textView.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    ArrayList<TaskModel> list;
    ProgressDialog loading;
    DatabaseReference references;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_dashboard);
        recyclerView=findViewById(R.id.rec);
        loading = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.texx);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new WorkerDashboardAdapter(this,list);
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
        references = FirebaseDatabase.getInstance().getReference("everyworker");
        this.references.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((TaskModel) dataSnapshot.getValue(TaskModel.class));
                }
                adapter.notifyDataSetChanged();
                if (WorkerDashboard.this.list.size() == 0) {
                    Toast.makeText(WorkerDashboard.this, "No taskss", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
                int total = 0;
                for (int i = 0; i < WorkerDashboard.this.list.size(); i++) {
                    total++;
                }
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(WorkerDashboard.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        return false;
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), WorkerDashboard.class));
                        finish();
                        return false;
                    /*case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        return false;*/
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), WorkerDashboard.class));
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}