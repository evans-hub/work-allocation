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
import android.widget.TextView;
import android.widget.Toast;

import com.example.workallocation.Adapters.AvailableAdapter;
import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Available extends AppCompatActivity {
    RecyclerView recyclerView;
    AvailableAdapter adapter;
    ArrayList<workModel> list;
    ProgressDialog loading;
    DatabaseReference reference;
    AlertDialog.Builder builds,build;
    TextView tt;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);
        recyclerView=findViewById(R.id.recycler);
        loading = new ProgressDialog(this);
        tt=findViewById(R.id.text);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        String dep=getIntent().getStringExtra("dep");
        if(dep.equalsIgnoreCase("finance")){
            tt.setText("Finance");
        }
        if(dep.equalsIgnoreCase("inquiries")){
            tt.setText("Inquiries");
        }
        if(dep.equalsIgnoreCase("others")){
            tt.setText("Others");
        }
        if(dep.equalsIgnoreCase("technical")){
            tt.setText("Technical");
        }
        if(dep.equalsIgnoreCase("admin")){
            tt.setText("Admin");
        }
        path=tt.getText().toString().trim();
        reference = FirebaseDatabase.getInstance().getReference("available");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new AvailableAdapter(this,list);
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
        this.reference.child(path).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add((workModel) dataSnapshot.getValue(workModel.class));
                    loading.dismiss();
                }
                adapter.notifyDataSetChanged();
                if (Available.this.list.size() == 0) {
                    AlertDialog alert = Available.this.builds.create();
                    alert.setTitle("Available workers");
                    alert.show();
                }
                int total = 0;
                for (int i = 0; i < Available.this.list.size(); i++) {
                    total++;
                }
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(Available.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        this.builds = new AlertDialog.Builder(this);
        this.builds.setMessage("Workers").setTitle("Available Workers");
        this.builds.setMessage("There are no available workers now").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                Intent intent=new Intent(Available.this, ViewActivity.class);
                startActivity(intent);
                finish();
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