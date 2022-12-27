package com.example.workallocation.Adapters;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;

import java.util.ArrayList;
public class DashAdapter extends RecyclerView.Adapter<DashAdapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> list;



    public DashAdapter(Context context2, ArrayList<TaskModel> list2) {
        this.context = context2;
        this.list = list2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.listing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TaskModel model = this.list.get(position);
        holder.mname.setText(model.getTitle());
        holder.mdesc.setText(model.getDescription());

    }


    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mname,mdesc;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.listing_name);
            this.mdesc = (TextView) itemView.findViewById(R.id.listing_desc);
            mdesc.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
