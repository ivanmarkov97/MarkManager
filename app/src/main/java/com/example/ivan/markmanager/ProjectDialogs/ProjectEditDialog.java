package com.example.ivan.markmanager.ProjectDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ivan.markmanager.MainActivity;
import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;
import com.example.ivan.markmanager.R;

/**
 * Created by Ivan on 21.03.2018.
 */

public class ProjectEditDialog extends DialogFragment implements View.OnClickListener{

    private Toolbar toolbar;
    private Button save;

    private ProjectAdapter adapter;
    private ProjectAdapter.ViewHolder viewHolder;

    public void setAdapter(ProjectAdapter adapter, ProjectAdapter.ViewHolder viewHolder) {
        this.adapter = adapter;
        this.viewHolder = viewHolder;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_change_view, null);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.change_project_toolbar);
        save = (Button) view.findViewById(R.id.change_project_change);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).setTitle("Change project");

        save.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.change_project_change:
                Toast.makeText(getContext(),  "CHANGED", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                dismiss();
                break;
        }
    }
}
