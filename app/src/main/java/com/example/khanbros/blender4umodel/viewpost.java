package com.example.khanbros.blender4umodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.whalemare.sheetmenu.SheetMenu;

public class viewpost extends AppCompatActivity {
    String commentviewurl2="http://10.0.2.2:8080/program/userssort2.php";
    RecyclerView recyclerView;EditText postcomment;VideoView videoView;ImageView imageView2,youtube;YouTubeThumbnailView youTubeThumbnailView;
    ArrayList<commentpostinfo> data=new ArrayList<>();ScrollView scrollView;
    TextView comments,title,question,name,datetime,postid;CircleImageView imageView,like,unlike,comment;
    ImageLoader imageLoader;RequestQueue requestQueue;Context context=viewpost.this;
String dtime;String s;RelativeLayout relativeLayout;ProgressBar progressBar;Bitmap bitmap=null;
    String email,email2;SharedPreferences sharedPreferences;TextView textlikes;
    commentrecycler  adapter;    String viewurl="http://10.0.2.2:8080/program/postidview.php";
ImageView more,play,filter;String words,text;
  String  likesurl="http://10.0.2.2:8080/program/likes.php";
    String  likescounturl="http://10.0.2.2:8080/program/countlikes.php";      String likeposturl="http://10.0.2.2:8080/program/likepost.php";
      String commentviewurl="http://10.0.2.2:8080/program/commentview.php";
       String  commentcounturl="http://10.0.2.2:8080/program/countcomment.php";
    String  deleteposturl="http://10.0.2.2:8080/program/deletepost.php"; String profileimg,photo,name2,dt,title2,ques;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpost);
        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Post Profile");
        declare();
        showvolley();


    }

    private void more() {
        if(email.equals(email2)){
            more.setVisibility(View.VISIBLE);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SheetMenu.with(context)
                            .setTitle("Select option")
                            .setMenu(R.menu.sheetmenu)
                            .setAutoCancel(true)
                            .setClick(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    int i=item.getItemId();
                                    if(i==R.id.edit){
                                        Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_SHORT).show();
                                        String no="1";
                                        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){

                                            Intent in=new Intent(context,postActivity.class);
                                            in.putExtra("email",email);
                                            in.putExtra("post",s);
                                            in.putExtra("no",no);
                                            in.putExtra("title",title.getText().toString());
                                            in.putExtra("postid",postid.getText().toString());
                                            context.startActivity(in);
                                        }
                                        else if(s.endsWith("mp4")||s.endsWith("3gp")){


                                            Intent in=new Intent(context,postActivity.class);
                                            in.putExtra("email",email);
                                            in.putExtra("post",s);
                                            in.putExtra("no",no);
                                            in.putExtra("title",title.getText().toString());

                                            in.putExtra("postid",postid.getText().toString());
                                            context.startActivity(in);

                                        }
                                        else if(s.startsWith("http")){

                                            Intent intent=new Intent(context,linkpostActivity.class);
                                            String hint="only youtube link";
                                            intent.putExtra("post",s);
                                            intent.putExtra("no",no);
                                            intent.putExtra("title",title.getText().toString());
                                            intent.putExtra("postid",postid.getText().toString());
                                            intent.putExtra("email",email);
                                            intent.putExtra("hint",hint);
                                            startActivity(intent);


                                        }

                                        else {

                                            Intent intent=new Intent(context,linkpostActivity.class);
                                            String hint="text";
                                            intent.putExtra("post",s);
                                            intent.putExtra("no",no);
                                            intent.putExtra("title",title.getText().toString());
                                            intent.putExtra("postid",postid.getText().toString());
                                            intent.putExtra("email",email);
                                            intent.putExtra("hint",hint);
                                            startActivity(intent);


                                        }

                                    }
                                    if(i==R.id.delete){
                                        AlertDialog.Builder a=new AlertDialog.Builder(viewpost.this);
                                        a.setMessage(" Delete this post in the page");
                                        a.setIcon(R.drawable.blender);
                                        a.setPositiveButton("delete",new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deletepost();
                                            }
                                        });
                                        a.setNegativeButton("cancel",new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                     dialog.cancel();
                                            }
                                        });

                                       a.create().show();

                                    }
                                    return false;
                                }
                            }).show();
                }
            });
        }else {
            more.setVisibility(View.GONE);

        }
    }
    private void deletepost() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest request = new StringRequest(Request.Method.POST,deleteposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context,response,Toast.LENGTH_SHORT).show();

                if(response.equalsIgnoreCase("delete")){
                    finish();
                    Intent intent=new Intent(context,MainRnewsfeedActivity.class);

                    startActivity(intent);

                }
                else{

                       Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
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

                map.put("postid", postid.getText().toString());

                return map;

            }
        };
        requestQueue.add(request);
    }
    private void likecount() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest request = new StringRequest(Request.Method.POST, likescounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    textlikes.setText(" ");


                }
                else{
                    if(a<=1000){

                        textlikes.setText(a+"likes");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    textlikes.setText(y + "k likes");
                                }
                                else {

                                    y=y.substring(0,3);
                                    textlikes.setText(y+"k likes");}}
                            else {
                                if(!y.contains(".")) {
                                    textlikes.setText(y + "k likes");
                                }
                                else {
                                    y=y.substring(0,4);
                                    textlikes.setText(y+"k likes");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                textlikes.setText(e + "M likes");
                            }
                            else {
                                e=e.substring(0,3);

                                textlikes.setText(e+"M likes");
                            }}

                    }

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

                map.put("postid", postid.getText().toString());

                return map;

            }
        };
        requestQueue.add(request);
    }
    private void commentcount() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest request = new StringRequest(Request.Method.POST, commentcounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //    Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    comments.setText(" ");
                    filter.setVisibility(View.GONE);


                }
                else{
                    if(a<=1000){

                        comments.setText(a+"comments");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    comments.setText(y + "k comments");
                                }
                                else {

                                    y=y.substring(0,3);
                                    comments.setText(y+"k comments");}}
                            else {
                                if(!y.contains(".")) {
                                    comments.setText(y + "k comments");
                                }
                                else {
                                    y=y.substring(0,4);
                                    comments.setText(y+"k comments");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                comments.setText(e + "M comments");
                            }
                            else {
                                e=e.substring(0,3);

                                comments.setText(e+"M comments");
                            }}

                    }

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

                map.put("postid", postid.getText().toString());

                return map;

            }
        };
        requestQueue.add(request);
    }

    private void likeshow() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest request = new StringRequest(Request.Method.POST, likesurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


                if(response.equalsIgnoreCase("liked")){

                    like.setVisibility(View.INVISIBLE);
                    unlike.setVisibility(View.VISIBLE);
                }
                else if(response.equalsIgnoreCase("unliked")){
                    unlike.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
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

                map.put("postid", postid.getText().toString());
                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }



    private void like( final int no) {
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, likeposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
          //      Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                likecount();

                if(response.equalsIgnoreCase("liked")){
                    like.setVisibility(View.INVISIBLE);
                    unlike.setVisibility(View.VISIBLE);

                    Animation.like(context,unlike);

                    if(s.endsWith("jpg")||s.endsWith("jpeg")||s.endsWith("png")) {
                        final String img = "http://10.0.2.2:8080/program/" +s;


                        imageLoader.displayImage(img, imageView2, new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                bitmap = loadedImage;
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {

                            }
                        });}

                    else if(s.endsWith("3gp")||s.endsWith("mp4")){
                        final String img = "http://10.0.2.2:8080/program/" + s;
                        Glide.with(context)
                                .asBitmap().load(img)
                                .listener(new RequestListener<Bitmap>() {
                                              @Override
                                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                                  return false;
                                              }

                                              @Override
                                              public boolean onResourceReady(Bitmap map, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                                  bitmap=map;
                                                  return false;
                                              }
                                          }
                                ).submit();
                    }
                        else{ imageLoader.displayImage(photo, imageView, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            bitmap = loadedImage;
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });}
                    Handler h=new Handler();
                    Runnable r=new Runnable() {
                        @Override
                        public void run() {
                            if(email.equals(email2)){

                            }else{
                           // notification();
                            }
                        }
                    };
                 h.postDelayed(r,1000);
                }
                else if(response.equalsIgnoreCase("unliked")){
                    unlike.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
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
                map.put("no", String.valueOf(no));
                map.put("postid", postid.getText().toString());
                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }
    public void showcomment() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, commentviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);



                        String   name = post.getString("name");
                        String    imagepath = post.getString("profileimg");
                        String comment = post.getString("comment");
                        String   datetime=post.getString("date_time");
                        String commentid=post.getString("commentid");
                        getdata(name,imagepath,comment,datetime,commentid);

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

                map.put("postid", postid.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);




    }
    public void showcomment2() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, commentviewurl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);



                        String   name = post.getString("name");
                        String    imagepath = post.getString("profileimg");
                        String comment = post.getString("comment");
                        String   datetime=post.getString("date_time");
                        String commentid=post.getString("commentid");
                        getdata(name,imagepath,comment,datetime,commentid);

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

                map.put("postid", postid.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);




    }
    public  void  notification(){
        if(s.endsWith("jpg")||s.endsWith("jpeg")||s.endsWith("png")||s.endsWith("mp4")||s.endsWith("3gp")) {

            int id = 0;
            Intent in = new Intent(context, viewpost.class);
            in.putExtra("postid", postid.getText().toString());
            in.putExtra("back","0");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, in, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification n = new NotificationCompat.Builder(context)
                    .setContentTitle(" like your post")
                    .setTicker("BLENDER 4U").setColor(6767767)
                    .setLargeIcon(bitmap).setOnlyAlertOnce(true).setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("your post liked in " + textlikes.getText().toString())

                            .setSummaryText(title.getText().toString()).bigLargeIcon(null)
                    )
                    .setSmallIcon(R.drawable.ic_profile)
                    .setContentText("your post liked in " +textlikes.getText().toString())
                    .setAutoCancel(true).setContentIntent(pendingIntent).build();

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(id, n);
        }

        else  {
            if(s.startsWith("http")){
                s=title.getText().toString();
            }

            int id = 1;
            Intent in = new Intent(context, viewpost.class);
            in.putExtra("postid", postid.getText().toString());
            in.putExtra("back","0");
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, id, in, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification n2 = new NotificationCompat.Builder(context)
                    .setContentTitle(" like your post")
                    .setTicker("BLENDER 4U").setColor(6767767)
                    .setLargeIcon(bitmap).setOnlyAlertOnce(true).setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("your post liked in " + textlikes.getText().toString())
                            .setSummaryText(title.getText().toString()).bigText(s)
                    )
                    .setSmallIcon(R.drawable.blender)
                    .setContentText("your post liked in " + textlikes.getText().toString())
                    .setAutoCancel(true).setContentIntent(pendingIntent2).build();

            NotificationManager nm2 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm2.notify(id, n2);
        }


}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("back").equals("0")){
            Intent in=new Intent(context,MainRnewsfeedActivity.class);
            context.startActivity(in);

        }
        if(getIntent().getStringExtra("back").equals("1")){
            Intent in=new Intent(context,showcaseActivity.class);
            context.startActivity(in);

        }
        if(getIntent().getStringExtra("back").equals("2")){
            Intent in=new Intent(context,userphoto.class);
            context.startActivity(in);

        }
        if(getIntent().getStringExtra("back").equals("3")){
            Intent in=new Intent(context,uservideo.class);
            context.startActivity(in);

        }
    }

    private void getdata(String name, String imagepath, String comment, String datetime, String comentid) {
        commentpostinfo current = new commentpostinfo();


        current.imagepath = imagepath;
        current.comment=comment;
        current.name = name;
        current.datetime = datetime;
        current.commentid = comentid;
        data.add(current);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        adapter = new commentrecycler(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        if(i==android.R.id.home){
            this.finish();
            if(getIntent().getStringExtra("back").equals("0")){
            Intent in=new Intent(context,MainRnewsfeedActivity.class);
                context.startActivity(in);

            }
            if(getIntent().getStringExtra("back").equals("1")){
                Intent in=new Intent(context,showcaseActivity.class);
                context.startActivity(in);

            }
            if(getIntent().getStringExtra("back").equals("2")){
                Intent in=new Intent(context,userphoto.class);
                context.startActivity(in);

            }
            if(getIntent().getStringExtra("back").equals("3")){
                Intent in=new Intent(context,uservideo.class);
                context.startActivity(in);

            }


        }

        return super.onOptionsItemSelected(item);
    }
    private void showvolley() {

        requestQueue= Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST, viewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);

                        String profileimg = post.getString("profileimg");
                        photo="http://10.0.2.2:8080/program/"+profileimg;

                          dt= post.getString("date_time");
                             s=post.getString("post");
                         name2=post.getString("name");
                         title2=post.getString("title");
                        email2=post.getString("email");

                        more();


                        name.setText(name2);


                        if(title2.length()>=200){
                            title.setTextSize(14);
                          title.setText(title2);
                        }
                        else if(title2.length()<=100){
                            title.setTextSize(22);
                         title.setText(title2);
                        }else {
                            title.setTextSize(18);
                           title.setText(title2);
                        }
                        String timestamp=getTimestampdifference(dt);
                        if(!timestamp.equals("0")){
                            int td= Integer.parseInt(timestamp);
                            if(td>=30){
                                int t=td/30;
                                if(t>=13){
                                    String g=dt;
                                    g=g.substring(0,10);
                                 datetime.setText(g);

                                }
                                else {
                                    int n=td/30;
                                    String e= String.valueOf(n);
                                    String o=e.substring(0,1);

                                    if(n>=9){
                                        e=e.substring(0,2);
                                       datetime.setText(e + " month ago");

                                    }
                                    else {
                                        datetime.setText(o + " month ago");

                                    }

                                }

                            }
                            else {
                                datetime.setText(timestamp + " days ago");
                            }
                        }
                        else {
                            if(dtime.equals("0")){
                              datetime.setText("Just Now");

                            }
                            else {
                                if(dtime.contains("-")){
                                   datetime.setText("TODAY");}
                                else {
                                   datetime.setText(dtime + " hours ago");
                                }
                            }
                        }

                        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){
                          question.setVisibility(View.INVISIBLE);  play.setVisibility(View.GONE);
                            videoView.setVisibility(View.INVISIBLE);
                          imageView2.setVisibility(View.VISIBLE);
                            youtube.setVisibility(View.INVISIBLE);
                           youTubeThumbnailView.setVisibility(View.INVISIBLE);
                           progressBar.setVisibility(View.GONE);

                            final String img="http://10.0.2.2:8080/program/"+s;

                            imageLoader= ImageLoader.getInstance();
                            imageLoader.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());
                            DisplayImageOptions options= new DisplayImageOptions.Builder()
                                    //    .cacheInMemory(true).cacheOnDisk(true)
                                    .showImageOnLoading(R.drawable.blender).build();

                            if(profileimg.startsWith("http")||profileimg.startsWith("con")){
                                imageLoader.displayImage(profileimg,imageView2,options);
                            }
                            else {
                                imageLoader.displayImage(img,imageView2,options);
                            }
                        }
                        else if(s.endsWith("mp4")||s.endsWith("3gp")){
                          question.setVisibility(View.INVISIBLE);
                          videoView.setVisibility(View.VISIBLE);  youtube.setVisibility(View.GONE);
                             play.setVisibility(View.VISIBLE);
                            imageView2.setVisibility(View.INVISIBLE);
                         youTubeThumbnailView.setVisibility(View.INVISIBLE);



                            final String vide="http://10.0.2.2:8080/program/"+s;

                            // MediaController mc=new MediaController(context);
                            // mc.setAnchorView(myviewholder.videoView);
                            //   mc.setMediaPlayer(myviewholder.videoView);
                            Uri video=Uri.parse(vide);
                            // myviewholder.videoView.setMediaController(mc);
                           videoView.setVideoURI(video);
                           videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                               @Override
                               public void onPrepared(MediaPlayer mp) {
                                   progressBar.setVisibility(View.GONE);
                                   mp.setVolume(0,0);
                                   videoView.start();
                               }
                           });


                        }
                        else if(s.startsWith("https://youtu.be")){
                            youtube.setVisibility(View.VISIBLE);
                            play.setVisibility(View.GONE);
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
                                            progressBar.setVisibility(View.GONE);
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
                            play.setVisibility(View.GONE);
                            youtube.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.GONE);
                            question.setVisibility(View.VISIBLE);
                            videoView.setVisibility(View.INVISIBLE);
                           imageView2.setVisibility(View.INVISIBLE);
                           youTubeThumbnailView.setVisibility(View.INVISIBLE);


                                question.setText(s);


                        }

                        imageLoader= ImageLoader.getInstance();
                        imageLoader.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());
                        DisplayImageOptions options= new DisplayImageOptions.Builder()
                                //  .cacheInMemory(true).cacheOnDisk(true)
                                .showImageOnLoading(R.drawable.blender).build();


                        imageLoader.displayImage(photo, imageView, options);




                        //    Picasso.get().load(photo).into(circleImageView);


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

                map.put("postid", postid.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);



    }
    private String getTimestampdifference(String sdate) {
        Date date=new Date();
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        Date todaytime=calendar.getTime();
        s.format(todaytime);
        String diff=" ";
        Date timestramp;
        try {
            timestramp=s.parse(sdate);
            diff=String.valueOf(Math.round((todaytime.getTime()-timestramp.getTime())/1000/60/60/24));
            if(diff.equals("0")){
                dtime=String.valueOf(todaytime.getHours()-timestramp.getHours());
                return diff;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diff;
    }
    private void declare() {
        filter=findViewById(R.id.ree);
        play=findViewById(R.id.imageViewplay);
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        relativeLayout= (RelativeLayout) findViewById(R.id.r);
        videoView= (VideoView) findViewById(R.id.videoView2);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        youTubeThumbnailView= (YouTubeThumbnailView) findViewById(R.id.y);
       youtube= (ImageView) findViewById(R.id.imageViewyb);
        postcomment= (EditText) findViewById(R.id.editTextcmt);
        name= (TextView) findViewById(R.id.textViewname);
        comments= (TextView) findViewById(R.id.textcommentss);
        title= (TextView) findViewById(R.id.textViewtitle);
        question= (TextView) findViewById(R.id.textViewquestion);
        imageView= (CircleImageView) findViewById(R.id.img);
        datetime= (TextView) findViewById(R.id.textViewdate);
        postid= (TextView) findViewById(R.id.textViewpostid);

        sharedPreferences=getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(getResources().getString(R.string.email),"");

postid.setText(getIntent().getStringExtra("postid"));
        like= (CircleImageView) findViewById(R.id.img2);
        comment= (CircleImageView) findViewById(R.id.img3);
        unlike= (CircleImageView) findViewById(R.id.img4);
        textlikes= (TextView) findViewById(R.id.textViewlikes2);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question.getVisibility()==View.VISIBLE){

                }
                else {
                    Intent in=new Intent(context,ViewActivity.class);
                    in.putExtra("post",s);
                    context.startActivity(in);
                }
            }
        });
      postcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent in=new Intent(context,postcommentActivity.class);
                    in.putExtra("post",s);
                in.putExtra("postid",postid.getText().toString());
                    context.startActivity(in);

            }
        });
       comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(context,postcommentActivity.class);
                in.putExtra("post",s);
                in.putExtra("postid",postid.getText().toString());
                context.startActivity(in);

            }
        });
        likecount();likeshow();showcomment();commentcount();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(1);
            }
        });
        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(0);
            }
        });
more=findViewById(R.id.imageViewmore);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w= title.getText().toString();
                if(w.contains("http://")||w.contains("https://")) {
                    // Toast.makeText(getApplicationContext(),w.indexOf("http") + " semma", Toast.LENGTH_LONG).show();
                    int a = w.indexOf("http");


                    int k=a+1;String f="";
                    title.setLinkTextColor(Color.BLUE);
                    lable:while (w.charAt(k)!=' '){
                        f=w.substring(a,k+1);
                        k++;
                        continue lable;
                    }
                    Intent i=new Intent(context,webActivity.class);
                    i.putExtra("url",f);
                    startActivity(i);

                }
            }
        });
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w= question.getText().toString();
                if(w.contains("http://")||w.contains("https://")) {
                    // Toast.makeText(getApplicationContext(),w.indexOf("http") + " semma", Toast.LENGTH_LONG).show();
                    int a = w.indexOf("http");


                    int k=a+1;String f="";
                    question.setLinkTextColor(Color.BLUE);
                    lable:while (w.charAt(k)!=' '){
                        f=w.substring(a,k+1);
                        k++;
                        continue lable;
                    }
                    Intent i=new Intent(context,webActivity.class);
                    i.putExtra("url",f);
                    startActivity(i);

                }
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                View vv=getLayoutInflater().inflate(R.layout.filter,null);


                alertDialog.setIcon(R.drawable.ic_filter2);
                alertDialog.setTitle("filter");
                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        text=words;
                        spinner();
                    }
                });

                Spinner spinner=vv.findViewById(R.id.spinner);
                String[] a={"All","High LIKES","public","Date Format"};
                ArrayAdapter<String> s=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,a);
                spinner.setAdapter(s);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        words=parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                alertDialog.setView(vv);
                AlertDialog dialog=alertDialog.create();
                dialog.show();
            }
        });

    }
    public  void spinner() {
        if (text.equals("public")) {
            data.clear();

            showcomment();
        }
        if (text.equals("High LIKES")) {
            data.clear();

            showcomment2();
        }

    }
}
