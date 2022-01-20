package com.alarm.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hadist_list extends AppCompatActivity {

    //inisialisasi
    private RecyclerView rec;
    private List<Hadist> hadists ;
    private adapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadist_list);

        rec = findViewById(R.id.hadist_list);
        hadists = new ArrayList<>();

        extras();


    }

    //proses mengambil data dari api
    private void extras() {
        String data = getIntent().getStringExtra("Hadist");
        String url = "https://api.hadith.sutanlab.id/books/"+data+"?range=1-300";
        Log.d("data",url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("hadiths");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Hadist hadist = new Hadist();
                        hadist.setNumber(jsonObject.getString("number").toString());
                        hadist.setArab(jsonObject.getString("arab").toString());
                        hadist.setId(jsonObject.getString("id").toString());

                        hadists.add(hadist);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                myadapter = new adapter(getApplicationContext(),hadists);
                rec.setAdapter(myadapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}