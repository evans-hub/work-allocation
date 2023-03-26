package com.example.workallocation.utils;

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

import com.example.workallocation.Adapters.AssignedAdapter;
import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.example.workallocation.UI.ProfileActivity;
import com.example.workallocation.utils.Admin.AdminDashboard;
import com.example.workallocation.utils.Admin.ViewAllWorkers;
import com.example.workallocation.utils.Admin.ViewAllTasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignedActivity extends AppCompatActivity {
    RecyclerView recycle;
    AssignedAdapter adapter;
    ArrayList<TaskModel> list;
    ProgressDialog loading;
    DatabaseReference reference;
    TextView vail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned);
        recycle=findViewById(R.id.recyclervieww);
        loading = new ProgressDialog(this);
        vail=findViewById(R.id.available);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        reference = FirebaseDatabase.getInstance().getReference("tasks");
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new AssignedAdapter(this,list);
        recycle.setAdapter(adapter);
        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        this.reference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((TaskModel) dataSnapshot.getValue(TaskModel.class));
                }
                adapter.notifyDataSetChanged();
                if (AssignedActivity.this.list.size() == 0) {
                    Toast.makeText(AssignedActivity.this, "No tasks", Toast.LENGTH_SHORT).show();
                }
                int total = 0;
                for (int i = 0; i < AssignedActivity.this.list.size(); i++) {
                    total++;
                }
                vail.setText(String.valueOf(total) + " Available Tasks");
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(AssignedActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), ViewAllTasks.class));
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
                        startActivity(new Intent(getApplicationContext(), ViewAllWorkers.class));
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}