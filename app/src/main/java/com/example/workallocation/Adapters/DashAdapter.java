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

import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;
import com.example.workallocation.utils.Design2;
import com.example.workallocation.utils.User.Design4;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class DashAdapter extends RecyclerView.Adapter<DashAdapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> list;
    FirebaseAuth mAuth;
    String subjects;



    public DashAdapter(Context context2, ArrayList<TaskModel> list2) {
        this.context = context2;
        this.list = list2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.dash, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TaskModel model = this.list.get(position);
        holder.mname.setText(model.getTitle());
        holder.mdesc.setText(model.getDescription());
        holder.mstart.setText(model.getStartdate());
        holder.mend.setText(" - "+model.getEnddate());
        mAuth=FirebaseAuth.getInstance();
        DatabaseReference referencesfgf =  FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
        referencesfgf.child(model.getTaskId()).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
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
        });
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context.getApplicationContext(), Design4.class);
                intent.putExtra("id",model.getTaskId());
                intent.putExtra("dep",model.getDepartment());
                intent.putExtra("desc",model.getDescription());
                intent.putExtra("name",model.getTitle());
                intent.putExtra("start",model.getStartdate());
                intent.putExtra("end",model.getEnddate());


                context.startActivity(intent);
            }
        });


    }


    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mname,mdesc,mstart,mend;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.listing_name);
            this.mdesc = (TextView) itemView.findViewById(R.id.listing_desc);
            this.mstart = (TextView) itemView.findViewById(R.id.lll_start);
            this.mend = (TextView) itemView.findViewById(R.id.lll_end);
            btn=itemView.findViewById(R.id.accept);

            mdesc.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
