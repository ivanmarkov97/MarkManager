package com.example.ivan.markmanager.ProjectDialogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivan.markmanager.MainActivity;
import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;
import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.UserAuthClass;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ivan on 22.03.2018.
 */

public class ProjectCreateDialog extends DialogFragment {

    Button add;
    Toolbar toolbar;
    EditText name;
    EditText description;
    ProjectAdapter adapter;
    ArrayList<Project> projects;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_add_view, container, false);

        add = (Button) view.findViewById(R.id.add_project_add);
        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.add_project_toolbar);
        name = (EditText) view.findViewById(R.id.add_project_name);
        description = (EditText) view.findViewById(R.id.add_project_description);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).setTitle("Create project");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.add_project_add){
                    ProjectCreateHandler projectCreateHandler = new ProjectCreateHandler(getContext(), getActivity());
                    projectCreateHandler.execute();
                }
            }
        });

        return view;
    }

    public void setAdapter(ProjectAdapter adapter) {
        this.adapter = adapter;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public class ProjectCreateHandler extends AsyncTask<String, String, String> {

        private Context context;
        private Activity activity;
        private ProgressDialog progressDialog;
        String _name = "";
        String _description = "";

        public ProjectCreateHandler(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
            this.progressDialog = new ProgressDialog(this.activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _name = name.getText().toString();
            _description = description.getText().toString();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", _name);
                jsonObject.put("description", _description);
                jsonObject.put("created_at", "2018-03-21T12:46:17Z");
                jsonObject.put("owner", UserAuthClass.getId());
            } catch (JSONException e){;}
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(context.getResources().getString(R.string.web_server) + "/api/v1.0/projects/?user=" + UserAuthClass.getId())
                    .addHeader("Authorization", "Token " + UserAuthClass.getToken())
                    .addHeader("Content-Type", "application/json")
                    .post(body)
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
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            try{
                if(!_name.equals("") && !_description.equals("")) {
                    projects.add(0, new Project(_name, _description, "2018-03-21T12:46:17Z", Integer.valueOf(UserAuthClass.getId())));
                    adapter.notifyDataSetChanged();
                    dismiss();
                }
            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
