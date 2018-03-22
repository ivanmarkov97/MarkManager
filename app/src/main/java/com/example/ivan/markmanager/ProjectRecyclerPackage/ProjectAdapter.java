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
import java.util.Collections;

/**
 * Created by Ivan on 20.03.2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> implements ItemTouchHelperAdapter{

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
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @Override
    public boolean onItemMove(int from, int to) {
        Collections.swap(projects, from, to);
        notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onItemRemove(int pos) {
        projects.remove(pos);
        notifyItemRemoved(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView projectName;

        public ViewHolder(final View itemView) {
            super(itemView);

            projectName = (TextView) itemView.findViewById(R.id.project_item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getId() == itemView.getId()){
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, new TaskListFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }
}
