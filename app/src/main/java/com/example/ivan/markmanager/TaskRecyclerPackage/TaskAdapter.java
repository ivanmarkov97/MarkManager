package com.example.ivan.markmanager.TaskRecyclerPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.TaskActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 20.03.2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private ArrayList<Task> tasks;
    private Context context;

    public TaskAdapter(ArrayList<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskName.setText(task.getName());
        holder.taskDeadline.setText(task.getDeadline());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView taskName;
        TextView taskDeadline;

        public ViewHolder(final View itemView) {
            super(itemView);

            taskName = (TextView) itemView.findViewById(R.id.task_item_name);
            taskDeadline = (TextView) itemView.findViewById(R.id.task_item_deadline);
        }
    }
}
