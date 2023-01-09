package com.example.workallocation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.Entity.workModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.Admin.ViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.MyViewHolder> {
    Context context;
    ArrayList<workModel> list;
    String assignx,depx,endx,startx,filex,datex,titlex,descx,state,sdesc,status;



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
                DatabaseReference refer = FirebaseDatabase.getInstance().getReference("alltasks").child(taskId);
                refer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
assignx=snapshot.child("assigned_to").getValue(String.class);
depx=snapshot.child("department").getValue(String.class);
descx=snapshot.child("description").getValue(String.class);
endx=snapshot.child("enddate").getValue(String.class);
startx=snapshot.child("startdate").getValue(String.class);
filex=snapshot.child("file").getValue(String.class);
datex=snapshot.child("taskdate").getValue(String.class);
titlex=snapshot.child("title").getValue(String.class);
sdesc=snapshot.child("s_desc").getValue(String.class);
status=snapshot.child("status").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                refer.child("assigned_to").setValue(model.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        TaskModel mode = new TaskModel(titlex, descx, depx, startx, endx, datex, taskId, sdesc, assignx,filex,status);
                        DatabaseReference referal = FirebaseDatabase.getInstance().getReference("everyworker").child(model.getId()).child(taskId);
                        referal.setValue(mode);
                        DatabaseReference referall = FirebaseDatabase.getInstance().getReference("workers").child(model.getId());
                        referall.child("availability").setValue("Unavailable");
                        referal.child(taskId).child(model.getId()).setValue("Assigned");
                        DatabaseReference data=FirebaseDatabase.getInstance().getReference("alltasks").child(taskId);
                        data.child("status").setValue("Assigned");
                        DatabaseReference dataa=FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
                        dataa.child("status").setValue("Assigned");
                        dataa.child("assigned_to").setValue(model.getId());
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("available").child(model.getDep()).child(model.getId());
                        reference.removeValue();
                        holder.mstate.setText("ASSIGNED");
                        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("available").child(model.getDep()).child(model.getId());
                        ref.removeValue();
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
                        reff.removeValue();*/

                        Intent intent1=new Intent(context.getApplicationContext(), ViewActivity.class);
                        context.startActivity(intent1);
                        ((Activity) context).finish();
                    }
                });


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

