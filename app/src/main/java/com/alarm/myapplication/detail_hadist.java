package com.alarm.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class detail_hadist extends AppCompatActivity {

    TextView id, arab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hadist);
        String dataId = getIntent().getStringExtra("id");
        String dataArab = getIntent().getStringExtra("arab");
        id = findViewById(R.id.id);
        arab = findViewById(R.id.arab);

        arab.setText(dataArab);
        id.setText(dataId);


    }
}