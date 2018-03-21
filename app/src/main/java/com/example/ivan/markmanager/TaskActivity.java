package com.example.ivan.markmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.ivan.markmanager.TaskRecyclerPackage.Task;
import com.example.ivan.markmanager.TaskRecyclerPackage.TaskAdapter;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    RecyclerView recyclerViewTasks;
    android.support.design.widget.FloatingActionButton floatingActionButton;

    TaskAdapter taskAdapter;
    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerViewTasks = (RecyclerView) findViewById(R.id.activity_task_recyclerview);
        floatingActionButton = (android.support.design.widget.FloatingActionButton) findViewById(R.id.activity_task_add_task);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        for(int i = 0; i < 10; i++){
            tasks.add(new Task(
                    "Task" + i,
                    "",
                    "",
                    "20.03.2018",
                    0,
                    0,
                    null,
                    null,
                    0,
                    0
            ));

            taskAdapter = new TaskAdapter(tasks, getApplicationContext());
            recyclerViewTasks.setAdapter(taskAdapter);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.activity_task_add_task){
                    startActivity(new Intent(view.getContext(), AddTaskActivity.class));
                }
            }
        });
    }
}
