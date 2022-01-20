package com.alarm.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class activity_hadist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadist);


    }

    public void Bukhari(View view) {
        Intent intent = new Intent(this, Hadist_list.class);
        intent.putExtra("Hadist","bukhari");
        startActivity(intent);
    }

    public void Muslim(View view) {
        Intent intent = new Intent(this,  Hadist_list.class);
        intent.putExtra("Hadist","muslim");
        startActivity(intent);
    }

    public void Tirmdiz(View view) {
        Intent intent = new Intent(this,  Hadist_list.class);
        intent.putExtra("Hadist","tirmidzi");
        startActivity(intent);
    }

    public void Malik(View view) {
        Intent intent = new Intent(this,  Hadist_list.class);
        intent.putExtra("Hadist","malik");
        startActivity(intent);
    }
}