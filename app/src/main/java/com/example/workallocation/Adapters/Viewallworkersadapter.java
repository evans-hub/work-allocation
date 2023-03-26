package com.example.workallocation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.Admin.WorkerDetailsFromAd;

import java.util.ArrayList;

public class Viewallworkersadapter extends RecyclerView.Adapter<Viewallworkersadapter.MyViewHolder> {
    Context context;
    ArrayList<workModel> list;
    String subjects;



    public Viewallworkersadapter(Context context2, ArrayList<workModel> list2) {
        this.context = context2;
        this.list = list2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.ll, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewallworkersadapter.MyViewHolder holder, int position) {
        workModel model = this.list.get(position);
        holder.mname.setText(model.getName());
        holder.mdesc.setText(model.getDep());
        holder.mavail.setText(model.getAvailability());
       /* DatabaseReference referencesfgf =  FirebaseDatabase.getInstance().getReference("workers");
        referencesfgf.child("availability").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects=snapshot.getValue(String.class);
                if (subjects.equalsIgnoreCase("accepted")){
                    holder.btn.setText("Complete");
                }
                else if(subjects.equalsIgnoreCase("Assigned")){
                    holder.btn.setText("Accept");
                }
                else if(subjects.equalsIgnoreCase("Completed")){
                    holder.btn.setText("Completed");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context.getApplicationContext(), WorkerDetailsFromAd.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("dep",model.getDep());
                intent.putExtra("phone",model.getPhone());
                intent.putExtra("avail",model.getAvailability());
                intent.putExtra("name",model.getName());
                intent.putExtra("emai",model.getEmail());

                context.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mname,mdesc,mavail;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.y_name);
            this.mdesc = (TextView) itemView.findViewById(R.id.y_desc);
            mavail=itemView.findViewById(R.id.y_avail);
            mdesc.setMovementMethod(new ScrollingMovementMethod());
            btn=itemView.findViewById(R.id.y_status);
        }
    }
}

