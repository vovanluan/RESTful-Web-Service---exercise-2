package com.example.luan.restfulclient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import com.example.luan.restfulclient.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login, signup;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/restful-java/user/login";
                String query = "";
                try {
                    query = String.format("param1=%s&param2=%s",
                            URLEncoder.encode(username.getText().toString(), "UTF-8"),
                            URLEncoder.encode(password.getText().toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String REQUEST_URL = url + "?" + query;
                new LoginRequest().execute(REQUEST_URL);
                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("User", (Parcelable) user);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong username or password", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private class LoginRequest extends AsyncTask<String, Void, Void> {

        private String jsonResponse;

        @Override
        protected Void doInBackground(String... urls) {
            try {
                // Step 1 : Create a HttpURLConnection object send REQUEST to server
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Step 2: wait for incoming RESPONSE stream, place data in a buffer
                InputStream isResponse = urlConnection.getInputStream();
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(isResponse));

                // Step 3: Arriving JSON fragments are concatenate into a StringBuilder
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseBuffer.readLine()) != null){
                    stringBuilder.append(line);
                }
                jsonResponse = stringBuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                // Step 4 : Convert JSON string to User object
                Gson gson = new Gson();
                Type  type = new TypeToken<User>(){}.getType();
                user = gson.fromJson(jsonResponse, type);
            } catch (Exception e) {

            }
        }
    }

}

