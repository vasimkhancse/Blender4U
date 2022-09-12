package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

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

public class uservideo extends AppCompatActivity {
    TextView EmailShow;
    String selectedPath;
    String EmailHolder;
    SharedPreferences sharedPreferences;
    String posttt;
    showcaserecycler adapter;
    String imagepath;
    Button LogOut;
    RecyclerView recyclerView;
    ArrayList<showcase> data = new ArrayList<>();
    String postid;RequestQueue requestQueue;
    String postviewurl = "http://10.0.2.2:8080/program/userpostviewvideo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uservideo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("VIDEO");
        EmailShow = (TextView) findViewById(R.id.EmailShow);

        sharedPreferences = getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        EmailHolder = sharedPreferences.getString(getResources().getString(R.string.email), "");
        EmailShow.setText(EmailHolder);


        showquestion();
        if(getIntent().getStringExtra("no").equals("1")){
            EmailShow.setText(getIntent().getStringExtra("email"));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
            this.finish();
            if(getIntent().getStringExtra("no").equals("1")){
                Intent intent = new Intent(this,friendprofileActivity.class);
                intent.putExtra("email",EmailShow.getText().toString());
                startActivity(intent);
            }else {
                Intent intent = new Intent(this,profileActivity.class);

                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        if(getIntent().getStringExtra("no").equals("1")){
            Intent intent = new Intent(this,friendprofileActivity.class);
            intent.putExtra("email",EmailShow.getText().toString());
            startActivity(intent);
        }else {
            Intent intent = new Intent(this,profileActivity.class);

            startActivity(intent);
        }
    }

    public void showquestion() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST, postviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);



                        posttt = post.getString("post");

                        postid = post.getString("postid");
                        getdata(posttt, postid);


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

                map.put("email", EmailShow.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);



    }

    private void getdata(String post, String postid) {
        showcase current = new showcase();


        current.post = post;

        current.postid = postid;
        data.add(current);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        adapter = new showcaserecycler(this, data,"3");
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //   staggeredGridLayoutManager.setMeasuredDimension(400,200);
        //  staggeredGridLayoutManager.setSpanCount(3);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager.setGapStrategy(2);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

    }
}