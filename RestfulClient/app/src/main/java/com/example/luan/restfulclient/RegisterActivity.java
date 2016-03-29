package com.example.luan.restfulclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, confirmPassword, email, name;
    Button signup;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        signup = (Button) findViewById(R.id.signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password not match. Please type again", Toast.LENGTH_LONG).show();
                    return;
                }
                // Create a user object
                user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.setName(name.getText().toString());
                String url = "http://localhost:8080/restful-java/user/add";
                new RegisterRequest().execute(url);
            }
        });
    }

    private class RegisterRequest extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            try {
                // Create connection
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Convert this object to json string using gson
                Gson gson = new Gson();
                Type type = new TypeToken<User>(){}.getType();
                String json = gson.toJson(user, type);

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(json);
                wr.flush();

                int result = urlConnection.getResponseCode();
                if (result == HttpURLConnection.HTTP_OK) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("User", (Parcelable) user);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error: " + urlConnection.getResponseMessage(), Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
