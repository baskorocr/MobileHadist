package com.alarm.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exit(View view) {
        finish();
    }

    public void adzan(View view) {
        Intent intent = new Intent(this, Activity_Jadwal.class);
        startActivity(intent);
    }

    public void hadist(View view) {
        Intent intent = new Intent(this, activity_hadist.class);
        startActivity(intent);
    }
}