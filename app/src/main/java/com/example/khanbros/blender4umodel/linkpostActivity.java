package com.example.khanbros.blender4umodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class linkpostActivity extends AppCompatActivity {
TextView email;EditText title,epost;Button post,text,link;String datetime;RequestQueue requestQueue;
    String posturl="http://10.0.2.2:8080/program/post.php"; boolean CheckEditText;
    String posturl2="http://10.0.2.2:8080/program/editcontent.php",postid;YouTubeThumbnailView youTubeThumbnailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkpost);
        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Post");
        email= (TextView) findViewById(R.id.textViewemail);
        email.setText(getIntent().getStringExtra("email"));
        title= (EditText) findViewById(R.id.editTexttitle);
        epost= (EditText) findViewById(R.id.editTextpost);youTubeThumbnailView=findViewById(R.id.y);
        epost.setHint(getIntent().getStringExtra("hint"));

        Date d=new Date();
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
        datetime=(s.format(d));



        if(getIntent().getStringExtra("no").equals("1")){
            title.setText(getIntent().getStringExtra("title"));
            epost.setText(getIntent().getStringExtra("post"));
            postid=getIntent().getStringExtra("postid");

            if(epost.getHint().equals("only youtube link")){
                final String ss=epost.getText().toString();
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                youTubeThumbnailView.initialize("AIzaSyABQYpFQwEc5RAr924e37Wvg1XEKhI_KJI", new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        youTubeThumbnailLoader.setVideo(ss.substring(17,28));
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                //  youTubeThumbnailView.setImageResource(R.drawable.blender);
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        youTubeThumbnailView.setImageResource(R.drawable.blender);

                    }
                });
            }
        }


    }
    private void post2() {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, posturl2, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("successfully")){
                 linkpostActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(),viewpost.class);
                    intent.putExtra("back","0");
                    intent.putExtra("postid",postid);


                    startActivity(intent);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("no", "2");

                map.put("title", title.getText().toString());
                map.put("post", epost.getText().toString());
                map.put("postid", postid);
                return map;

            }
        };
        requestQueue.add(request);
    }
    public void CheckEditTextIsEmptyOrNot(){

        String tit = title.getText().toString();

        final String posttt = epost.getText().toString();



        if(TextUtils.isEmpty(tit)||TextUtils.isEmpty(posttt)  )
        {

            CheckEditText = false;

        }
        else {
          if(epost.getHint().equals("only youtube link"))
          {
             if(posttt.startsWith("https://youtu.be")){

                 CheckEditText = true ;


             }
             else {
                 CheckEditText = false;
             }
          }
          else{
              CheckEditText = true ;
          }

        }

    }
    private void post() {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("success")){
               linkpostActivity.this.finish();
                    Intent in=new Intent(linkpostActivity.this,MainRnewsfeedActivity.class);

                    startActivity(in);
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
                map.put("title", title.getText().toString());
                map.put("no", String.valueOf(3));
                map.put("date_time", datetime);
                map.put("email", email.getText().toString());
                map.put("post",epost.getText().toString());

                return map;

            }
        };
        requestQueue.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        if(i==android.R.id.home){
            this.finish();

        }
        if(i==R.id.post){
            CheckEditTextIsEmptyOrNot();
            if(CheckEditText){
                if(getIntent().getStringExtra("no").equals("1")){
                    post2();
                }
                else {
                post();
                }

            }
            else {
                if(epost.getHint().equals("only youtube link")){
                Toast.makeText(getApplicationContext(), "not a link(or)Please fill all form fields.", Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(getApplicationContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
            }
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
