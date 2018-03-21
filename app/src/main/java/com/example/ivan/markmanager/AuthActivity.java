package com.example.ivan.markmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button signIn;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        login = (EditText) findViewById(R.id.auth_activity_login);
        password = (EditText) findViewById(R.id.auth_activity_password);
        signIn = (Button) findViewById(R.id.auth_activity_enter);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.auth_activity_enter) {
                    /*
                    * post request to django application for token and ID
                    * */
                    String username = login.getText().toString();
                    String pass = password.getText().toString();
                    OkHttpAuthHandler okHttpAuthHandler = new OkHttpAuthHandler(AuthActivity.this);
                    okHttpAuthHandler.execute(username, pass);
                    Toast.makeText(view.getContext(), username + " " + pass, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class OkHttpAuthHandler extends AsyncTask<String, String, String> {

        private Activity activity;
        private ProgressDialog progressDialog;

        public OkHttpAuthHandler(Activity activity) {
            this.activity = activity;
            progressDialog = new ProgressDialog(this.activity);
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
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", strings[0]);
                jsonObject.put("password", strings[1]);
            } catch (JSONException e){;}
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(getResources().getString(R.string.web_server) + "/auth/token")
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
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try{
                JSONObject jsonResponse;
                jsonResponse = new JSONObject(s);
                UserAuthClass.setId(jsonResponse.getString("id"));
                UserAuthClass.setToken(jsonResponse.getString("token"));
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //finish();
            }catch (JSONException e){
                Toast.makeText(getApplicationContext(), "Неверный логин иои пароль", Toast.LENGTH_LONG).show();
            }
        }
    }
}
