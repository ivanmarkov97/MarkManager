package com.example.ivan.markmanager.TaskFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.markmanager.MainActivity;
import com.example.ivan.markmanager.ProjectFragments.ProjectCreateView;
import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;
import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.TaskRecyclerPackage.Task;
import com.example.ivan.markmanager.TaskRecyclerPackage.TaskAdapter;

import java.util.ArrayList;

/**
 * Created by Ivan on 21.03.2018.
 */

public class TaskListFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView recyclerViewProjects;
    android.support.design.widget.FloatingActionButton floatingActionButton;

    TaskAdapter taskAdapter;
    ArrayList<Task> tasks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_view, container, false);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.task_list_toolbar);
        recyclerViewProjects = (RecyclerView) view.findViewById(R.id.task_list_recyclerview);
        floatingActionButton = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.task_list_add_task);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).setTitle("Tasks");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        tasks = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            tasks.add(new Task(
                    "Task" + i,
                    "",
                    "20.03.2018",
                    "21.03.2018",
                    0,
                    0,
                    null,
                    null,
                    0,
                    0
            ));
        }

        taskAdapter = new TaskAdapter(tasks, getFragmentManager());

        recyclerViewProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProjects.setHasFixedSize(true);
        recyclerViewProjects.setAdapter(taskAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.task_list_add_task){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, new TaskCreateView(), "TaskCreateView")
                            .addToBackStack("TaskCreateView")
                            .commit();
                }
            }
        });

        return view;
    }
}
