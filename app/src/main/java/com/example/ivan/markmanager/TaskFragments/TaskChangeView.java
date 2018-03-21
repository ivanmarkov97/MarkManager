package com.example.ivan.markmanager.TaskFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.markmanager.MainActivity;
import com.example.ivan.markmanager.R;

/**
 * Created by Ivan on 21.03.2018.
 */

public class TaskChangeView extends Fragment {

    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_change_view, container, false);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.change_task_toolbar);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).setTitle("Change task");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return view;
    }
}
