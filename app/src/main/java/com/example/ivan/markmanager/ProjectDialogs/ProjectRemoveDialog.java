package com.example.ivan.markmanager.ProjectDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;
import com.example.ivan.markmanager.ProjectRecyclerPackage.ProjectAdapter;
import com.example.ivan.markmanager.R;
import com.example.ivan.markmanager.UserAuthClass;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ivan.markmanager.ProjectDialogs.ProjectCreateDialog.JSON;

/**
 * Created by Ivan on 21.03.2018.
 */

public class ProjectRemoveDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private ProjectAdapter adapter;
    private int pos;
    private ArrayList<Project> projects;

    public void setAdapter(ProjectAdapter adapter, int pos) {
        this.adapter = adapter;
        this.pos = pos;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity())
                .setMessage("Remove project?")
                .setPositiveButton("Yes", this)
                .setNegativeButton("No", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case Dialog.BUTTON_POSITIVE:
                //adapter.onItemRemove(pos);
                ProjectRemoveHandler removeHandler = new ProjectRemoveHandler(getContext(), getActivity());
                removeHandler.execute();
                break;
            case Dialog.BUTTON_NEGATIVE:
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        dismiss();
    }

    public class ProjectRemoveHandler extends AsyncTask<String, String, String> {

        private Context context;
        private Activity activity;
        private ProgressDialog progressDialog;

        public ProjectRemoveHandler(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
            this.progressDialog = new ProgressDialog(this.activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Log.d("MyTAG", "" + pos);
            //Toast.makeText(context, "" + projects.get(viewHolder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("owner", UserAuthClass.getId());
            }catch (JSONException e){;}
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(context.getResources().getString(R.string.web_server) + "/api/v1.0/projects/" + projects.get(pos).getId())
                    .addHeader("Authorization", "Token " + UserAuthClass.getToken())
                    .addHeader("Content-Type", "application/json")
                    .delete(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try{
                projects.remove(pos);
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
