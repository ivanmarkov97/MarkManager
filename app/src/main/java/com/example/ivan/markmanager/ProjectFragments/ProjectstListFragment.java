package com.example.ivan.markmanager.ProjectFragments;

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
import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;
import com.example.ivan.markmanager.R;

import java.util.ArrayList;

/**
 * Created by Ivan on 20.03.2018.
 */

public class ProjectstListFragment extends Fragment {

    RecyclerView recyclerViewProjects;
    android.support.design.widget.FloatingActionButton floatingActionButton;
    Toolbar toolbar;

    ProjectAdapter projectAdapter;
    ArrayList<Project> projects = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.projects_list_view, container, false);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.project_list_toolbar);
        recyclerViewProjects = (RecyclerView) view.findViewById(R.id.project_list_recyclerview);
        floatingActionButton = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.projects_list_add_project);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).setTitle("Projects");

        projects = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            projects.add(new Project(
                    "Project" + i,
                    "Nope",
                    "20.03.2018",
                    1
            ));
        }

        projectAdapter = new ProjectAdapter(projects, getFragmentManager());

        recyclerViewProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProjects.setHasFixedSize(true);
        recyclerViewProjects.setAdapter(projectAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.projects_list_add_project){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, new ProjectCreateView(), "ProjectCreateView")
                            .addToBackStack("ProjectCreateView")
                            .commit();
                }
            }
        });

        return view;
    }
}
