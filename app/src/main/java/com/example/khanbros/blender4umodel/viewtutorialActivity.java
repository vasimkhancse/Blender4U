package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewtutorialActivity extends AppCompatActivity {
RequestQueue requestQueue;RecyclerView recyclerView;  Context context = viewtutorialActivity.this;
    String viewurl="http://10.0.2.2:8080/program/tutorial.php";String title;
    ArrayList<youtubedata> data=new ArrayList<>();ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        title=getIntent().getStringExtra("title");

        getSupportActionBar().setTitle(title);

        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.r);
        showupdates();
    }
    public void showupdates() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, viewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);



                        String    imagepath = post.getString(title);


                        getdata(imagepath);
                        progressBar.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                return map;

            }
        };
        requestQueue.add(request);




    }
    private void getdata( String imagepath) {
       youtubedata current = new youtubedata();


        current.setId(imagepath);


        data.add(current);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        youtuberecycler youtuberecycler = new youtuberecycler(data, context,1);
        recyclerView.setAdapter(youtuberecycler);
        youtuberecycler.notifyDataSetChanged();


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();

            Intent in = new Intent(this, tutorialActivity.class);
            startActivity(in);
        }

            return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent in = new Intent(this, tutorialActivity.class);
        startActivity(in);
    }
}
