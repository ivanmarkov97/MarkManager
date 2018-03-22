package com.example.ivan.markmanager.ProjectFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ivan.markmanager.MainActivity;
import com.example.ivan.markmanager.ProjectDialogs.ProjectCreateDialog;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ItemTouchHelperCallback;
import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;
import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.UserAuthClass;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by Ivan on 20.03.2018.
 */

public class ProjectstListFragment extends Fragment {

    RecyclerView recyclerViewProjects;
    android.support.design.widget.FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProjectAdapter projectAdapter;
    ArrayList<Project> projects = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        projectAdapter = new ProjectAdapter(projects, getFragmentManager());
        ProjectLoadHandler projectLoadHandler = new ProjectLoadHandler(getContext(), getActivity());
        projectLoadHandler.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.projects_list_view, container, false);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.project_list_toolbar);
        recyclerViewProjects = (RecyclerView) view.findViewById(R.id.project_list_recyclerview);
        floatingActionButton = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.projects_list_add_project);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).setTitle("Projects");

        projectAdapter = new ProjectAdapter(projects, getFragmentManager());

        recyclerViewProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProjects.setHasFixedSize(true);
        recyclerViewProjects.setAdapter(projectAdapter);

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(projectAdapter, getContext(), getFragmentManager());
        callback.setProjects(projects);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerViewProjects);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.projects_list_add_project){
                    /*FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, new ProjectCreateView(), "ProjectCreateView")
                            .addToBackStack("ProjectCreateView")
                            .commit();*/

                    ProjectCreateDialog dialog = new ProjectCreateDialog();
                    dialog.setAdapter(projectAdapter);
                    dialog.setProjects(projects);
                    dialog.show(getFragmentManager(), "ProjectCreateDialog");
                }
            }
        });

        return view;
    }

    public class ProjectLoadHandler extends AsyncTask<String, String, String> {

        private Context context;
        private Activity activity;
        private ProgressDialog progressDialog;

        public ProjectLoadHandler(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
            this.progressDialog = new ProgressDialog(this.activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(context.getResources().getString(R.string.web_server) + "/api/v1.0/projects/?user=" + UserAuthClass.getId())
                    .addHeader("Authorization", "Token " + UserAuthClass.getToken())
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            try{
                JSONArray array = new JSONArray(s);
                for(int i = 0; i < array.length(); i++) {
                    projects.add(new Project(
                            array.getJSONObject(i).getString("name"),
                            array.getJSONObject(i).getString("description"),
                            array.getJSONObject(i).getString("created_at"),
                            array.getJSONObject(i).getInt("id")
                    ));
                    projectAdapter.notifyItemInserted(i);
                }
                //Toast.makeText(context, "" + projectAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
            }catch (JSONException e){
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
