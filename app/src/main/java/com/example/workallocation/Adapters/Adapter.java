package com.example.workallocation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workallocation.utils.Worker.Design;
import com.example.workallocation.Entity.TaskModel;
import com.example.workallocation.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> list;
    int day;
    String nn="";
    String dayofmonth;
    String month;
    String bb;



    public Adapter(Context context2, ArrayList<TaskModel> list2) {
        this.context = context2;
        this.list = list2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.draw, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TaskModel model = this.list.get(position);
        holder.mname.setText(model.getTitle());
        holder.mplate.setText(model.getS_desc());


        DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date rr=df.parse(model.getEnddate());
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(rr.getTime());
            day= cal.get(Calendar.DAY_OF_WEEK);
            dayofmonth = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            month= cal.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault());
            switch (month){
                case "January":
                    bb="Jan";
                    break;
                case "February":
                    bb="Feb";
                    break;
                case "March":
                    bb="Mar";
                    break;
                case "April":
                    bb="Apr";
                    break;
                case "May":
                    bb="May";
                    break;
                case "June":
                    bb="Jun";
                    break;
                case "July":
                    bb="Jul";
                    break;
                case "August":
                    bb="Aug";
                    break;
                case "September":
                    bb="Sep";
                    break;
                case "October":
                    bb="Oct";
                    break;
                case "November":
                    bb="Nov";
                    break;
                case "December":
                    bb="Dec";
                    break;

            }
            switch (day){
                case 1:
                    nn="Sun";
                    break;
                case 2:
                    nn="Mon";
                    break;
                case 3:
                    nn="Tue";
                    break;
                case 4:
                    nn="Wed";
                    break;
                case 5:
                    nn="Thur";
                    break;
                case 6:
                    nn="Fri";
                    break;
                case 7:
                    nn="Sat";
                    break;


            }

            holder.mday.setText(nn);
            holder.myr.setText(dayofmonth);
            holder.mmonth.setText(bb);
            holder.mhrs.setText(model.getStartdate());
            holder.mmins.setText(model.getTaskId());
            holder.msecs.setText(model.getAssigned_to());
            holder.assign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context.getApplicationContext(), Design.class);
                    intent.putExtra("id",model.getTaskId());
                    intent.putExtra("dep",model.getDepartment());
                    intent.putExtra("desc",model.getDescription());
                    intent.putExtra("name",model.getTitle());
                    intent.putExtra("start",model.getStartdate());
                    intent.putExtra("end",model.getEnddate());

                    context.startActivity(intent);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mimage;
        TextView mmodel;
        TextView mname;
        TextView mplate;
        TextView mday;
        TextView mmonth;
        TextView myr;
        TextView mhrs,mmins,msecs;
        Button assign;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.d_title);
            this.mplate = (TextView) itemView.findViewById(R.id.d_description);
            this.mday = (TextView) itemView.findViewById(R.id.d_day);
            this.mmonth = (TextView) itemView.findViewById(R.id.d_month);
            this.myr = (TextView) itemView.findViewById(R.id.d_date);
            this.mhrs = (TextView) itemView.findViewById(R.id.s_date);
            this.mmins = (TextView) itemView.findViewById(R.id.s_time);
            this.msecs = (TextView) itemView.findViewById(R.id.e_time);
            assign=itemView.findViewById(R.id.status);
        }
    }
}
