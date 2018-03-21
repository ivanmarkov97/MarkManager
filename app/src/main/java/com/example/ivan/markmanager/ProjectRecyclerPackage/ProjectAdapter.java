package com.example.ivan.markmanager.ProjectRecyclerPackage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ivan.markmanager.ProjectFragments.ProjectChangeView;
import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.TaskFragments.TaskListFragment;

import java.util.ArrayList;

/**
 * Created by Ivan on 20.03.2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private ArrayList<Project> projects;
    private android.support.v4.app.FragmentManager fragmentManager;

    public ProjectAdapter(ArrayList<Project> projects, android.support.v4.app.FragmentManager fragmentManager) {
        this.projects = projects;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Project project = projects.get(position);
        holder.projectName.setText(project.getName());
        holder.projectChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, new ProjectChangeView(), "ProjectChangeView")
                        .addToBackStack("ProjectChangeView")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView projectName;
        ImageButton projectChange;

        public ViewHolder(final View itemView) {
            super(itemView);

            projectName = (TextView) itemView.findViewById(R.id.project_item_name);
            projectChange = (ImageButton) itemView.findViewById(R.id.project_item_change);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getId() == itemView.getId()){
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, new TaskListFragment(), "ProjectListFragment")
                                .addToBackStack("ProjectListFragment")
                                .commit();
                    }
                }
            });
        }
    }
}
