package com.example.workallocation.utils.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.workallocation.Adapters.Viewallworkersadapter;
import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.UI.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllWorkers extends AppCompatActivity {
    RecyclerView recyclerView;
    Viewallworkersadapter adapter;
    ArrayList<workModel> list;
    ProgressDialog loading;
    DatabaseReference reference;
    AlertDialog.Builder builds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_workers);
        recyclerView=findViewById(R.id.recy);
        loading = new ProgressDialog(this);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        reference = FirebaseDatabase.getInstance().getReference("workers");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new Viewallworkersadapter(this,list);
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
                    list.add((workModel) dataSnapshot.getValue(workModel.class));
                    if (!ViewAllWorkers.this.isFinishing() && loading != null) {
                        loading.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                if (ViewAllWorkers.this.list.size() == 0) {
                    AlertDialog alert = ViewAllWorkers.this.builds.create();
                    alert.setTitle("Available workers");
                    alert.show();
                }
                int total = 0;
                for (int i = 0; i < ViewAllWorkers.this.list.size(); i++) {
                    total++;
                }/*
                TextView textView = ViewActivity.this.tt;
                textView.setText(String.valueOf(total) + " cars");*/
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(ViewAllWorkers.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        this.builds = new AlertDialog.Builder(this);
        this.builds.setMessage("Workers").setTitle("Available Workers");
        this.builds.setMessage("There are no available workers now").setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                Intent intent=new Intent(ViewAllWorkers.this, AdminDashboard.class);
                startActivity(intent);
                finish();
            }
        }).setPositiveButton("Add Worker", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(ViewAllWorkers.this, AddWorkers.class);
                startActivity(intent);
                finish();
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
                        startActivity(new Intent(getApplicationContext(), AddWorkers.class));
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}