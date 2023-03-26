package com.example.workallocation.utils.Admin;

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

import com.example.workallocation.Adapters.ViewAllTasksAdapterr;
import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.example.workallocation.UI.SplashScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllTasks extends AppCompatActivity {
    RecyclerView recyclerView;
    ViewAllTasksAdapterr adapter;
    ArrayList<TaskModel> list;
    ProgressDialog loading;
    DatabaseReference reference;
    TextView vail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tasks);
        recyclerView=findViewById(R.id.recyclerview);
        loading = new ProgressDialog(this);
        vail=findViewById(R.id.avail);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        reference = FirebaseDatabase.getInstance().getReference("tasks");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new ViewAllTasksAdapterr(this,list);
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
        this.reference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((TaskModel) dataSnapshot.getValue(TaskModel.class));
                }
                adapter.notifyDataSetChanged();
                if (ViewAllTasks.this.list.size() == 0) {
                    Toast.makeText(ViewAllTasks.this, "No tasks", Toast.LENGTH_SHORT).show();
                }
                int total = 0;
                for (int i = 0; i < ViewAllTasks.this.list.size(); i++) {
                    total++;
                }
                vail.setText(String.valueOf(total) + " Available Tasks");
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(ViewAllTasks.this, "Error", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(getApplicationContext(), SplashScreen.class));
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