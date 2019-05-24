package com.example.lynford.personal_notes_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lynford.personal_notes_app.R;

public class MainActivity  extends AppCompatActivity{

    String username;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

    }
}
