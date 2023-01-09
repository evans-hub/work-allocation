package com.example.workallocation.utils.Worker;

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

import com.example.workallocation.Adapters.DashAdapter;
import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.Search;
import com.example.workallocation.utils.ProfileActivity;
import com.example.workallocation.utils.SplashScreen;
import com.example.workallocation.utils.User.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    DashAdapter adapter;
    ArrayList<TaskModel> list;
    ProgressDialog loading;
    Query references;
    FirebaseAuth mAuth;
    String id,s1;
    private TextView textView,which;

    @Override
    protected void onStart() {
        super.onStart();
        s1 = getSharedPreferences("MySharedPref", 0).getString("id_number", "");
        textView.setText("idid"+s1);
        String nn=mAuth.getCurrentUser().getEmail();
        which.setText(nn);

        /*mAuth=FirebaseAuth.getInstance();
        String nn=mAuth.getCurrentUser().getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users").child("client");
        reference.child(nn).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id=snapshot.getValue(String.class);
                textView.setText(id);
                Toast.makeText(Dashboard.this, "idss"+textView.getText().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView=findViewById(R.id.recyclerr);
        loading = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.textv);
        which=findViewById(R.id.usern);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new DashAdapter(this,list);
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
        references =  FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((TaskModel) dataSnapshot.getValue(TaskModel.class));
                }
                adapter.notifyDataSetChanged();
                if (Dashboard.this.list.size() == 0) {
                    Toast.makeText(Dashboard.this, "No tasks", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
                int total = 0;
                for (int i = 0; i < Dashboard.this.list.size(); i++) {
                    total++;
                }
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(Dashboard.this, "Error", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                        return false;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        return false;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), Task.class));
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}