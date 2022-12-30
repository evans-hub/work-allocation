package com.example.workallocation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.ViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.MyViewHolder> {
    Context context;
    ArrayList<workModel> list;



    public AvailableAdapter(Context context2, ArrayList<workModel> list2) {
        this.context = context2;
        this.list = list2;
    }

    public AvailableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AvailableAdapter.MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        workModel model = this.list.get(position);
        holder.mname.setText(model.getName());
        holder.mdesc.setText(model.getDep());
        holder.mstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = ((Activity) context).getIntent();
                String taskId=intent.getExtras().getString("id");
                ((Activity) context).setResult(((Activity) context).RESULT_OK,
                        intent);
                DatabaseReference refer = FirebaseDatabase.getInstance().getReference("alltasks").child(taskId).child("assigned_to");
                refer.setValue(model.getName()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DatabaseReference referal = FirebaseDatabase.getInstance().getReference("everyworker").child(model.getId());
                        referal.setValue(model);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("available").child(model.getDep()).child(model.getId());
                        ref.removeValue();
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
                        reff.removeValue();
                        Toast.makeText(context, "task assigned", Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(context.getApplicationContext(), ViewActivity.class);
                        context.startActivity(intent1);
                        ((Activity) context).finish();
                    }
                });

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("available").child(model.getId());
               reference.removeValue();
                           holder.mstate.setText("ASSIGNED");
            }
        });
    }


    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button mstate;
        TextView mdesc;
        TextView mname;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.y_name);
            mstate=itemView.findViewById(R.id.y_status);
            mdesc=itemView.findViewById(R.id.y_desc);
        }
    }
}

