package com.example.ivan.markmanager.TaskRecyclerPackage;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ivan.markmanager.ProjectFragments.ProjectChangeView;
import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.TaskFragments.TaskChangeView;
import com.example.ivan.markmanager.TaskFragments.TaskListFragment;

import java.util.ArrayList;

/**
 * Created by Ivan on 20.03.2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private ArrayList<Task> tasks;
    private FragmentManager fragmentManager;

    public TaskAdapter(ArrayList<Task> tasks, FragmentManager fragmentManager) {
        this.tasks = tasks;
        this.fragmentManager = fragmentManager;
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
        holder.taskChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, new TaskChangeView(), "TaskChangeView")
                        .addToBackStack("TaskChangeView")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView taskName;
        ImageButton taskChange;

        public ViewHolder(final View itemView) {
            super(itemView);

            taskName = (TextView) itemView.findViewById(R.id.task_item_name);
            taskChange = (ImageButton) itemView.findViewById(R.id.task_item_change);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getId() == itemView.getId()){
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, new TaskListFragment(), "TaskListFragment")
                                .addToBackStack("TaskListFragment")
                                .commit();
                    }
                }
            });
        }
    }
}
