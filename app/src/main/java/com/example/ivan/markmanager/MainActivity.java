package com.example.ivan.markmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ivan.markmanager.ProjectFragments.ProjectstListFragment;
import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewProjects;
    android.support.design.widget.FloatingActionButton floatingActionButton;

    ProjectAdapter projectAdapter;
    ArrayList<Project> projects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new ProjectstListFragment(), "ProjectsListFragment")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            getSupportFragmentManager().popBackStack();
        }
        return true;
    }
}
