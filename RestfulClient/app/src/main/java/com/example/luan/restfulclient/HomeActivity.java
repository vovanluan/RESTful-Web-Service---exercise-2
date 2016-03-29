package com.example.luan.restfulclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import entity.User;

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

/*        username.setText(user.getUsername());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        name.setText(user.getName());*/
    }
}
