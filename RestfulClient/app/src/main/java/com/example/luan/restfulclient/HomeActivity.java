package com.example.luan.restfulclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        User user = (User) getIntent().getSerializableExtra("User");
    }
}
