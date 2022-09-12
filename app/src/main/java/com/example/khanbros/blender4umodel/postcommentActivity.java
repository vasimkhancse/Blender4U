package com.example.khanbros.blender4umodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class postcommentActivity extends AppCompatActivity {
SharedPreferences sharedPreferences;String email;  TextView question;String datetime,s,postid;
    RequestQueue requestQueue;VideoView videoView;ImageView imageView2;YouTubeThumbnailView youTubeThumbnailView;
    EditText comment;    String posturl="http://10.0.2.2:8080/program/commentpost.php";
    boolean CheckEditText;ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcomment);

        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Comment post");
        sharedPreferences=getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(getResources().getString(R.string.email),"");

        question= (TextView) findViewById(R.id.textViewquestion);
        videoView= (VideoView) findViewById(R.id.videoView2);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        youTubeThumbnailView= (YouTubeThumbnailView) findViewById(R.id.y);
        postid=getIntent().getStringExtra("postid");
        s=getIntent().getStringExtra("post");

        comment= (EditText) findViewById(R.id.editTexttitle);

        requestQueue= Volley.newRequestQueue(this);

        Date d=new Date();
        SimpleDateFormat ss=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
        datetime=(ss.format(d));
        s=getIntent().getStringExtra("post");

        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){
            question.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.VISIBLE);

            youTubeThumbnailView.setVisibility(View.INVISIBLE);

            final String img="http://10.0.2.2:8080/program/"+s;

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.blender).build();

            if(s.startsWith("http")||s.startsWith("con")){
                imageLoader.displayImage(s,imageView2,options);
            }
            else {
                imageLoader.displayImage(img,imageView2,options);
            }
        }
        else if(s.endsWith("mp4")||s.endsWith("3gp")){
            question.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            youTubeThumbnailView.setVisibility(View.INVISIBLE);



            final String vide="http://10.0.2.2:8080/program/"+s;

            // MediaController mc=new MediaController(context);
            // mc.setAnchorView(myviewholder.videoView);
            //   mc.setMediaPlayer(myviewholder.videoView);
            Uri video=Uri.parse(vide);
            // myviewholder.videoView.setMediaController(mc);
            videoView.setVideoURI(video);
            videoView.seekTo(2000);

        }
        else if(s.startsWith("http")){
            question.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            youTubeThumbnailView.setVisibility(View.VISIBLE);
            youTubeThumbnailView.initialize("AIzaSyABQYpFQwEc5RAr924e37Wvg1XEKhI_KJI", new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(s.substring(17,28));
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
                    // youTubeThumbnailView.setImageResource(R.drawable.blender);

                }
            });


        }

        else {

            question.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            youTubeThumbnailView.setVisibility(View.INVISIBLE);


            question.setText(s);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        if(i==android.R.id.home){
            this.finish();
            Intent in=new Intent(this,viewpost.class);
            in.putExtra("back","0");
            in.putExtra("postid",postid);
            startActivity(in);

        }
        if(i==R.id.post){
            CheckEditTextIsEmptyOrNot();
            if(CheckEditText){
                post();

            }
            else {
                Toast.makeText(getApplicationContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void post() {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equalsIgnoreCase("post successfully")){
                   postcommentActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(),viewpost.class);
                    intent.putExtra("back","0");
                    intent.putExtra("postid",postid);
                    startActivity(intent);
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
                map.put("comment", comment.getText().toString());

                map.put("date_time", datetime);
                map.put("email", email);
                map.put("postid", postid);
                return map;

            }
        };
        requestQueue.add(request);
    }



    public void CheckEditTextIsEmptyOrNot(){

        String tit = comment.getText().toString();





        if(TextUtils.isEmpty(tit)  )
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
