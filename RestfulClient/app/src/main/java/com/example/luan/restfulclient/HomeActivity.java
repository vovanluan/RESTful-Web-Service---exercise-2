package com.example.luan.restfulclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import entity.User;
import support.Support;

public class HomeActivity extends AppCompatActivity {
    EditText username, password, email, name;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        User user = (User) getIntent().getSerializableExtra("User");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        update = (Button) findViewById(R.id.update);

        // Show user info
        username.setText(user.getUsername());
        username.setFocusable(false);
        username.setClickable(true);
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        name.setText(user.getName());



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a user object
                User u = new User();
                u.setUsername(username.getText().toString());
                u.setPassword(password.getText().toString());
                u.setEmail(email.getText().toString());
                u.setName(name.getText().toString());
                String url = "http://" + Support.HOST + ":8080/restful-java/user/updateInfo";
                UpdateRequest updateRequest = new UpdateRequest();
                updateRequest.u = u;
                updateRequest.execute(url);


            }
        });
    }
    private class UpdateRequest extends AsyncTask<String, Void, Integer> {

        public User u;
        @Override
        protected Integer doInBackground(String... urls) {
            try {
                Log.e("URL", urls[0]);
                // Create connection
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                // Convert this object to json string using gson
                Gson gson = new Gson();
                Type type = new TypeToken<User>(){}.getType();
                String json = gson.toJson(u, type);
                Log.e("Json", json);

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(json);
                wr.flush();
                wr.close();
                Log.e("Response Message", urlConnection.getResponseMessage());
                return urlConnection.getResponseCode();

            }
            catch (Exception e) {

            }
            return 0;
        }

    }
}
