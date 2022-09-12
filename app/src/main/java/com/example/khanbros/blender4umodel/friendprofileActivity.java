package com.example.khanbros.blender4umodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class friendprofileActivity extends AppCompatActivity {
    Context context=friendprofileActivity.this; TextView EmailShow,Edit;   String viewurl="http://10.0.2.2:8080/program/view.php";
     Button follow,following2;String profileimg,sname;
    ImageLoader imageLoader;CircleImageView circleImageView;
    String name,postid;String email;String followurl="http://10.0.2.2:8080/program/follow.php";
    String title;Button photo,video;String followposturl="http://10.0.2.2:8080/program/followpost.php";
    String qusetion; questionrecycler  adapter;

    String countposturl = "http://10.0.2.2:8080/program/countpost.php";
    String countfollowsurl = "http://10.0.2.2:8080/program/countfollows.php";
    String countfollowersurl = "http://10.0.2.2:8080/program/countfollowers.php";
    String postviewurl = "http://10.0.2.2:8080/program/userpostviewphoto.php";

    friendcaserecycler friendcaserecycler;RecyclerView rt;
    ArrayList<friendpostinfo> d=new ArrayList<>();   String usersviewurl="http://10.0.2.2:8080/program/userspost2.php";
       TextView media;
    RecyclerView recyclerView,recyclerView2;  ArrayList<questionpostinfo> data=new ArrayList<>();
    SearchView searchView; ArrayList<showcase> dat = new ArrayList<>();
    String imagepath;String datetime;TextView friends;

   SharedPreferences sharedPreferences;
    EditText post;RequestQueue requestQueue;
RelativeLayout relativeLayout,rl,r2;BroadcastReceiver broadcastReceiver;
    String questionviewurl="http://10.0.2.2:8080/program/userquestionview.php";
    String EmailHolder;TextView description2, following,follower,about,skill,contents,post3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofile);
        relativeLayout= (RelativeLayout) findViewById(R.id.r);
        rl= (RelativeLayout) findViewById(R.id.rl);
        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        isconnected();
        declare();

        showquestion();

        showvolley();

        countfollowers();countfollows();countpost();
        followshow();usersshow();showphoto();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        if(i==android.R.id.home){
            this.finish();



        }

        return super.onOptionsItemSelected(item);
    }
    public void isconnected()
    {
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(isconnect()==true){
                    if( relativeLayout.getVisibility()==View.VISIBLE){

                    }else{
                        relativeLayout.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        Snackbar.make(relativeLayout," INTERNET IS CONNECTED",Snackbar.LENGTH_SHORT).show();
                    }}
                else {
                    relativeLayout.setVisibility(View.GONE);

                    rl.setVisibility(View.VISIBLE);

                    Snackbar.make(relativeLayout,"NO INTERNET CONNECTION",Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public boolean isconnect(){
        ConnectivityManager c=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=c.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnectedOrConnecting()){
            NetworkInfo wifi=c.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile=c.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile!=null&&mobile.isConnectedOrConnecting())||(wifi!=null&&wifi.isConnectedOrConnecting())){
                return  true;
            }else {
                return false;
            }

        }else {
            return false;
        }}
    private void declare() {
          friends=findViewById(R.id.l);
          media=findViewById(R.id.media);
          rt=findViewById(R.id.o);recyclerView2=findViewById(R.id.j);
        EmailShow = (TextView)findViewById(R.id.EmailShow);
        follow=findViewById(R.id.follow);
        following2=findViewById(R.id.following);
        following = (TextView)findViewById(R.id.textViewfollow);
        follower = (TextView)findViewById(R.id.textViewfollowers);

        contents = (TextView)findViewById(R.id.textViewcontent);
        post3 = (TextView)findViewById(R.id.post);
        skill = (TextView)findViewById(R.id.textViewskills);
        description2 = (TextView)findViewById(R.id.textViewdescription);

        circleImageView= (CircleImageView) findViewById(R.id.img);
        sharedPreferences=getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        EmailHolder=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        EmailShow.setText(EmailHolder);
           email=getIntent().getStringExtra("email");
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,followersActivity.class);
                intent.putExtra("no","1");
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
       media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,mediaActivity.class);
                intent.putExtra("no","1");
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

       follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               follow(1);
            }
        });
        following2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow(0);
            }
        });


    }

    public void countpost() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, countposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    post3.setText(response);


                }
                else{
                    if(a<=1000){

                        post3.setText(a+"");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    post3.setText(y + "k");
                                }
                                else {

                                    y=y.substring(0,3);
                                    post3.setText(y+"k");}}
                            else {
                                if(!y.contains(".")) {
                                    post3.setText(y + "k");
                                }
                                else {
                                    y=y.substring(0,4);
                                    post3.setText(y+"k");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                post3.setText(e + "M");
                            }
                            else {
                                e=e.substring(0,3);

                                post3.setText(e+"M");
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

                map.put("email", email);
                return map;

            }
        };
        requestQueue.add(request);
    }
    public void countfollows() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, countfollowsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    following.setText(response);


                }
                else{
                    if(a<=1000){

                        following.setText(a+"");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    following.setText(y + "k");
                                }
                                else {

                                    y=y.substring(0,3);
                                    following.setText(y+"k");}}
                            else {
                                if(!y.contains(".")) {
                                    following.setText(y + "k");
                                }
                                else {
                                    y=y.substring(0,4);
                                    following.setText(y+"k");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                following.setText(e + "M");
                            }
                            else {
                                e=e.substring(0,3);

                                following.setText(e+"M");
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

                map.put("email", email);
                return map;

            }
        };
        requestQueue.add(request);
    }
    public void countfollowers() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, countfollowersurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    follower.setText(response);


                }
                else{
                    if(a<=1000){

                        follower.setText(a+"");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    follower.setText(y + "k");
                                }
                                else {

                                    y=y.substring(0,3);
                                    follower.setText(y+"k");}}
                            else {
                                if(!y.contains(".")) {
                                    follower.setText(y + "k");
                                }
                                else {
                                    y=y.substring(0,4);
                                    follower.setText(y+"k");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                follower.setText(e + "M");
                            }
                            else {
                                e=e.substring(0,3);

                                follower.setText(e+"M");
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

                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }


    public void showphoto() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST, postviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    if(res.getString("post").equals("null")&&post3.getText().toString().equals("0")){
                        media.setVisibility(View.GONE);
                    }
                    else {
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);



                        String  posttt = post.getString("post");

                        String  postid = post.getString("postid");
                        getdata(posttt, postid);


                    }}

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

                map.put("email", email);
                return map;

            }
        };
        requestQueue.add(request);



    }

    private void getdata(String post, String postid) {
        showcase current = new showcase();


        current.post = post;

        current.postid = postid;
        dat.add(current);
        Collections.reverse(dat);
        photosrecycler adapter = new photosrecycler(this, dat);
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }
    public void usersshow() {
        requestQueue= Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST,usersviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);


                      String  name = post.getString("name");
                      String  imagepath = post.getString("profileimg");
                       String title = post.getString("email");

                        getdata(name,imagepath,title);
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

                map.put("email", email);
                return map;

            }
        };
        requestQueue.add(request);




    }
    private void getdata( String name, String imagepath, String email) {
        friendpostinfo current=new friendpostinfo();

        current.email=email;
        current.imagepath=imagepath;

        current.name=name;

        d.add(current);

        friendcaserecycler=new friendcaserecycler(this,d,1);
        rt.setAdapter(friendcaserecycler);
        rt.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rt.setHasFixedSize(false);rt.setNestedScrollingEnabled(false);




    }
    private void followshow() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest request = new StringRequest(Request.Method.POST, followurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                  // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


                if(response.equalsIgnoreCase("follow")) {
                    following2.setVisibility(View.GONE);
                    follow.setVisibility(View.VISIBLE);
                }
                else if(response.equalsIgnoreCase("following")){
                    follow.setVisibility(View.GONE);
                    following2.setVisibility(View.VISIBLE);
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
                map.put("follow",EmailHolder);
                map.put("following", email);

                return map;

            }
        };
        requestQueue.add(request);
    }



    private void follow( final int no) {
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST,followposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                   //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


                if(response.equalsIgnoreCase("follow")) {
                   following2.setVisibility(View.GONE);
                  follow.setVisibility(View.VISIBLE);
                }
                else if(response.equalsIgnoreCase("following")){
                   follow.setVisibility(View.GONE);
                   following2.setVisibility(View.VISIBLE);
                }

                  followshow();

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
                map.put("following", email);
                map.put("name",sname);
                map.put("img",profileimg);
                map.put("follow",EmailHolder);
                return map;

            }
        };
        requestQueue.add(request);
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
                                String followers = post.getString("followers");
                                String following2 = post.getString("following");
                                String postss = post.getString("posts");
                                String skills = post.getString("skills");
                                String content = post.getString("content");
                                String description = post.getString("description");
                                 profileimg = post.getString("profileimg");
                                sname = post.getString("name");
                                String photo="http://10.0.2.2:8080/program/"+profileimg;

                                skills=skills.replace("."," ");
                                content=content.replace("."," ");
                                description=description.replace("."," ");


                                getSupportActionBar().setTitle(sname);


                                skill.setText(skills);
                                contents.setText(content);
                                description2.setText(description);
                                imageLoader=ImageLoader.getInstance();
                               imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
                                DisplayImageOptions options= new DisplayImageOptions.Builder()
                                     //   .cacheInMemory(true).cacheOnDisk(true)
                                        .showImageOnLoading(R.drawable.blender).build();

                                if(profileimg.startsWith("http")||profileimg.startsWith("con")){
                                    imageLoader.displayImage(profileimg,circleImageView,options);
                                }
                                else {
                                    imageLoader.displayImage(photo,circleImageView,options);
                                }

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

                        map.put("email", email);
                        return map;

                    }
                };
                requestQueue.add(request);


    }
    public void showquestion() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, questionviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject res=new JSONObject(response);
                    if(res.getString("post").equals("null")){
                      findViewById(R.id.textView4).setVisibility(View.GONE);
                    }
                    else {
                        JSONArray posts = res.getJSONArray("post");


                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject post = posts.getJSONObject(i);

                            qusetion = post.getString("question");
                            name = post.getString("name");
                            imagepath = post.getString("profileimg");
                            title = post.getString("title");
                            datetime = post.getString("date_time");
                            postid = post.getString("postid");
                            getdata(qusetion, name, imagepath, title, datetime, postid);
                        }
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

                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);




    }

    private void getdata(String qusetion, String name, String imagepath, String title, String datetime,String postid) {
        questionpostinfo current=new questionpostinfo();

        current.title=title;
        current.imagepath=imagepath;
        current.question=qusetion;
        current.name=name;
        current.datetime=datetime;
        current.postid=postid;
        data.add(current);
        Collections.reverse(data);
        recyclerView= (RecyclerView) findViewById(R.id.re);
        adapter=new questionrecycler(this,data,"1");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


    }




}
