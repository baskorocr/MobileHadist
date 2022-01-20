package com.alarm.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity_Jadwal extends AppCompatActivity {

    EditText input;
    Button cari;
    TextView txt,sekitar,subuh,dzuhur,ashar,magrib,isya;

    Boolean cek1,cek2;
    String tempInput1,tempInput2;
    String SqliQuery ;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        input = findViewById(R.id.input);
        cari = findViewById(R.id.cari);
        subuh = findViewById(R.id.JamSubuh);
        dzuhur = findViewById(R.id.JamDzuhur);
        ashar = findViewById(R.id.JamAshar);
        magrib = findViewById(R.id.JamMagrib);
        isya = findViewById(R.id.JamIsya);

        //Membuat database dan table
        sqLiteDatabase = openOrCreateDatabase("KotaDb", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mykota (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, kota VARCHAR);");

        //Menghitung apakah ada datanya
         cursor = sqLiteDatabase.rawQuery("SELECT kota FROM mykota ",null);
        if(cursor.getCount()>0)
        {
            String kota;
            if (cursor.moveToFirst()){
                    kota = cursor.getString(0);
                    input.setText(kota);
                    selectData(kota);
            }

        }
        cursor.close();

        //Jtidak ada akan menjalankan fungsi ini
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractdata();
            }
        });

    }

    //select untuk ditampilkan diinput
    private void selectData(String kota) {

        tempInput1 =kota;
        cek1(tempInput1);

        if(!cek1){
            Toast.makeText(this, "Masukkan kota", Toast.LENGTH_SHORT).show();
        }
        else {

            String urlGetId = "https://api.pray.zone/v2/times/today.json?city=" + tempInput1;
            Log.d("data: ", urlGetId);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetId, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONObject("results").getJSONArray("datetime");
                        Log.d("data: ", String.valueOf(jsonArray.length()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject("times");
                        subuh.setText(jsonObject.getString("Imsak").toString());
                        dzuhur.setText(jsonObject.getString("Dhuhr").toString());
                        ashar.setText(jsonObject.getString("Asr").toString());
                        magrib.setText(jsonObject.getString("Maghrib").toString());
                        isya.setText(jsonObject.getString("Isha").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void cek1(String tempInput1) {
        cek1 = !TextUtils.isEmpty(tempInput1);
    }



    //penyimpanan konfigurasi ketika tidak ada data, beserta update apabila ada data

    private void extractdata() {

        tempInput2 =input.getText().toString();
        cek2(tempInput2);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT kota FROM mykota ",null);

        if(cursor.getCount()==0){
            if(!cek2){
                Toast.makeText(this, "Masukkan kota", Toast.LENGTH_SHORT).show();
            }
            else {


                SqliQuery = "INSERT INTO mykota (kota) VALUES ('" + tempInput2 + "');";
                sqLiteDatabase.execSQL(SqliQuery);
                SqliQuery = "";

                String urlGetId = "https://api.pray.zone/v2/times/today.json?city=" + tempInput2;
                Log.d("data: ", urlGetId);

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetId, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONObject("results").getJSONArray("datetime");
                            Log.d("data: ", String.valueOf(jsonArray.length()));
                            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject("times");
                            subuh.setText(jsonObject.getString("Imsak").toString());
                            dzuhur.setText(jsonObject.getString("Dhuhr").toString());
                            ashar.setText(jsonObject.getString("Asr").toString());
                            magrib.setText(jsonObject.getString("Maghrib").toString());
                            isya.setText(jsonObject.getString("Isha").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);
                Toast.makeText(this, "Kota Berhasil di Simpan", Toast.LENGTH_SHORT).show();
            }
        }
        else{

            subuh.setText("");
            dzuhur.setText("");
            ashar.setText("");
            magrib.setText("");
            isya.setText("");

            ContentValues cv = new ContentValues();
            String p = tempInput2;
            cv.put("kota",p);

            Log.d("data", String.valueOf(cv));

            sqLiteDatabase.update("mykota", cv, "id = 1", null);
            String urlGetId = "https://api.pray.zone/v2/times/today.json?city=" + tempInput2;
            Log.d("data: ", urlGetId);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetId, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONObject("results").getJSONArray("datetime");
                        Log.d("data: ", String.valueOf(jsonArray.length()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject("times");
                        subuh.setText(jsonObject.getString("Imsak").toString());
                        dzuhur.setText(jsonObject.getString("Dhuhr").toString());
                        ashar.setText(jsonObject.getString("Asr").toString());
                        magrib.setText(jsonObject.getString("Maghrib").toString());
                        isya.setText(jsonObject.getString("Isha").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });


            requestQueue.add(jsonObjectRequest);

            Toast.makeText(this, "Data kota diupdate", Toast.LENGTH_SHORT).show();
        }

    }

    private void cek2(String tempInput2) {
        cek2 = !TextUtils.isEmpty(tempInput2);
    }


}