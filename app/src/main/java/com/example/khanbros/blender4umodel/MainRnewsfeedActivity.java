package com.example.khanbros.blender4umodel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainRnewsfeedActivity extends AppCompatActivity {
String u;
GifImageView gifImageView;RecyclerView rt;String res1,res2,res3;
FloatingActionButton f1,f2,f3;CircleImageView circleImageView;int y;
    String usersviewurl="http://10.0.2.2:8080/program/userspost(copy).php";friendcaserecycler friendcaserecycler;
    String exviewurl="http://10.0.2.2:8080/program/ex.php";ArrayList<friendpostinfo> d=new ArrayList<>();
String postviewurl="http://10.0.2.2:8080/program/postview.php";SwipeRefreshLayout swipeRefreshLayout;
    String name,postid;BroadcastReceiver broadcastReceiver;RelativeLayout rl;
    String title;RelativeLayout relativeLayout,r2;     int j=10;  searchFragment Fragment;
    String hintsviewurl="http://10.0.2.2:8080/program/hintsview.php";
    int size=0;GridView ss;           ArrayList<String> f=new ArrayList<>();
SharedPreferences s;
    String posttt;postrecycler  adapter;ProgressBar progressBar;LinearLayout k;
    String imagepath;String datetime,ex,postno;int first,last=10,no,totalsize;TextView tr;
    Button LogOut;  RecyclerView recyclerView;FrameLayout recyclerView2;  ArrayList<postinfo> data=new ArrayList<>();
   MaterialSearchView searchView;String postcounturl="http://10.0.2.2:8080/program/postcount.php";
    TextView EmailShow,t; String viewurl="http://10.0.2.2:8080/program/view.php";
    String EmailHolder;SharedPreferences sharedPreferences;Context context=MainRnewsfeedActivity.this;
RequestQueue requestQueue;ImageLoader imageLoader;FabSpeedDial fabSpeedDial;TextView hi;
boolean isscrolling=false;LinearLayoutManager linearLayoutManager;int curitems,tottalitems,scrolloutitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rnewsfeed);
        relativeLayout = findViewById(R.id.r);
        r2 = findViewById(R.id.rl2);
        rl = findViewById(R.id.rl);
        isconnected();
        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        declare();
        setupnavigation();
        showvolley();
        postcount();
        // send();
        usersshow();


        s=getSharedPreferences(splashActivity.k, Context.MODE_PRIVATE);
       u=  s.getString(getResources().getString(R.string.no),"");
       if(u.equals("1")){
           rt.setVisibility(View.GONE);

       }

    }


    private void send() {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,01);
        c.set(Calendar.MINUTE,32);
        c.set(Calendar.SECOND,10);

        Intent intent=new Intent(this,notification.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
    private void declare() {
        //friends=findViewById(R.id.yy);
        ss=findViewById(R.id.ss);
        rt=findViewById(R.id.rt);
        gifImageView=findViewById(R.id.i);
        tr=findViewById(R.id.u);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,followersActivity.class).putExtra("no","0"));
            }
        });

        try {
            InputStream inputStream=getAssets().open("blender.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout=findViewById(R.id.s);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    data.clear();
                    j = 10;
                    totalsize = no;
                    postview(j, totalsize);
                    // friends.setVisibility(View.VISIBLE);


                }
            });

        data.clear();
         progressBar=findViewById(R.id.progressBar7);
        circleImageView= (CircleImageView) findViewById(R.id.imageView);
        LogOut = (Button)findViewById(R.id.button);
        EmailShow = (TextView)findViewById(R.id.EmailShow);
        hi = (TextView)findViewById(R.id.textView6);
        recyclerView= (RecyclerView) findViewById(R.id.re);

        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                rt.setVisibility(View.GONE);
                SharedPreferences.Editor editor=s.edit();
                editor.putString(getResources().getString(R.string.no),"1");
                editor.commit();
                return false;
            }
        });
        recyclerView2= (FrameLayout) findViewById(R.id.rre);
        Date d=new Date();
        String s=String.valueOf(d.getHours());
        int t=Integer.parseInt(s);
        showhint(t);

        fabSpeedDial= (FabSpeedDial) findViewById(R.id.fabmenu);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem item) {

                int i=item.getItemId();

                if(i==R.id.link) {
                    Intent intent=new Intent(context,linkpostActivity.class);
                    String hint="only youtube link";
                    intent.putExtra("email",EmailShow.getText().toString());
                    intent.putExtra("hint",hint);
                    intent.putExtra("no"," ");
                    startActivity(intent);
                }
                if(i==R.id.text){
                    Intent intent=new Intent(context,linkpostActivity.class);
                    String hint="text";
                    intent.putExtra("email",EmailShow.getText().toString());
                    intent.putExtra("hint",hint);
                    intent.putExtra("no"," ");
                    startActivity(intent);
                }
                if(i==R.id.gallery){
                    Intent intent=new Intent(context,postActivity.class);
                    intent.putExtra("email",EmailShow.getText().toString());
                    intent.putExtra("no"," ");
                    startActivity(intent);
                }
                return false;
            }
        });


        searchView= (MaterialSearchView) findViewById(R.id.search);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                recyclerView.setVisibility(View.GONE);
                r2.setVisibility(View.GONE);tr.setVisibility(View.GONE);
                if(rt.getVisibility()==View.VISIBLE){
              rt.setVisibility(View.INVISIBLE);}
               ss.setVisibility(View.VISIBLE);
                  hintsshow();



            }

            @Override
            public void onSearchViewClosed() {
                r2.setVisibility(View.VISIBLE);tr.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(rt.getVisibility()==View.INVISIBLE){
                    rt.setVisibility(View.VISIBLE);

                } ss.setVisibility(View.GONE);f.clear();

            }
        });
        sharedPreferences=getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        EmailHolder=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        EmailShow.setText(EmailHolder);
        requestQueue= Volley.newRequestQueue(context);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences=getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();

                finish();
                Intent intent = new Intent(context,UserLoginActivity.class);

                startActivity(intent);

                Toast.makeText(context, "Log Out Successfully", Toast.LENGTH_LONG).show();


            }
        });
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
                        r2.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        Snackbar.make(relativeLayout," INTERNET IS CONNECTED",Snackbar.LENGTH_SHORT).show();
                    }}
                else {
                    relativeLayout.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
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
    private void showhint(int t) {
        if(t>=1&&t<=6){
            hi.setText("Hi Good NIGHT SWEET DREAMS");
        }
       else if(t>=6&&t<=9){
            hi.setText("Hi GOOD MORNING,HAVE A NICE DAY");
        }
        else if(t>=10&&t<=13){
            hi.setText("Hi GOOD MORNING,HAVE A NICE DAY");
        }
        else if(t>=13&&t<=16){
            hi.setText("Hi Good AFTERNOON");
        }
        else if(t>=16&&t<=20){
            hi.setText("Hi Good EVENING");
        }
        else if(t>=20&&t<=24){
            hi.setText("Hi Good NIGHT SWEET DREAMS");
        }

    }

    private void postcount() {

        requestQueue= Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST,postcounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("0")){
                    swipeRefreshLayout.setVisibility(View.GONE);
                    gifImageView.setVisibility(View.GONE);
                }
                postno=response;
                no=Integer.parseInt(postno);


               // Toast.makeText(getApplicationContext(),no+"",Toast.LENGTH_LONG).show();
                totalsize=no;

                postview(j,totalsize);


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
                        String photo="http://10.0.2.2:8080/program/"+profileimg;


                       imageLoader= ImageLoader.getInstance();
                        imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
                        DisplayImageOptions options= new DisplayImageOptions.Builder()
                                //  .cacheInMemory(true).cacheOnDisk(true)
                                .showImageOnLoading(R.drawable.blender).build();


                        if(profileimg.startsWith("http")){
                            imageLoader.displayImage(profileimg,circleImageView,options);
                        }
                        else {
                            imageLoader.displayImage(photo,circleImageView,options);
                        }



                     //    Picasso.get().load(photo).into(circleImageView);

                     //   showquestion();
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
    private void postview(final int k,final int size) {

        if(j==10){
        StringRequest request = new StringRequest(Request.Method.POST, exviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res1=response;
                int i,y;
               // Toast.makeText(MainRnewsfeedActivity.this, ""+k, Toast.LENGTH_SHORT).show();
                  int o=k;
                if (o-10 <=no){
                    try {
                        JSONObject res=new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");

                              y=size-k;
                               i=y+9;


                      while (i>=y){
                            JSONObject post = posts.getJSONObject(i);


                            posttt = post.getString("post");
                            name = post.getString("name");
                            imagepath = post.getString("profileimg");
                            title = post.getString("title");
                            datetime=post.getString("date_time");
                            postid=post.getString("postid");
                            String e=post.getString("email");
                            getdata(posttt,name,imagepath,title,datetime,postid,e,k);
                            progressBar.setVisibility(View.GONE);

                            swipeRefreshLayout.setRefreshing(false);
                            gifImageView.setVisibility(View.GONE);
                        i--;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }}
                else if(no<=9) {

                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");


                        for (int p = 0; p < posts.length(); p++) {
                            JSONObject post = posts.getJSONObject(p);


                            posttt = post.getString("post");
                            name = post.getString("name");
                            imagepath = post.getString("profileimg");
                            title = post.getString("title");
                            datetime = post.getString("date_time");
                            postid = post.getString("postid");
                            String e = post.getString("email");
                            getdata(posttt, name, imagepath, title, datetime, postid, e, k);
                            progressBar.setVisibility(View.GONE);

                            swipeRefreshLayout.setRefreshing(false);
                            gifImageView.setVisibility(View.GONE);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    progressBar.setVisibility(View.GONE);

                    // Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();
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

                map.put("email", EmailHolder);


                return map;

            }
        };
        requestQueue.add(request);}
        else {
            int i,y;
            // Toast.makeText(MainRnewsfeedActivity.this, ""+k, Toast.LENGTH_SHORT).show();
            int o=k;
            if (o-10 <=no){
                try {
                    JSONObject res=new JSONObject(res1);
                    JSONArray posts = res.getJSONArray("post");

                    y=size-k;
                    i=y+9;


                    while (i>=y){
                        JSONObject post = posts.getJSONObject(i);


                        posttt = post.getString("post");
                        name = post.getString("name");
                        imagepath = post.getString("profileimg");
                        title = post.getString("title");
                        datetime=post.getString("date_time");
                        postid=post.getString("postid");
                        String e=post.getString("email");
                        getdata(posttt,name,imagepath,title,datetime,postid,e,k);
                        progressBar.setVisibility(View.GONE);

                        swipeRefreshLayout.setRefreshing(false);
                        gifImageView.setVisibility(View.GONE);
                        i--;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }}

            else {
                progressBar.setVisibility(View.GONE);

                // Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();
            }

        }

    }


    private void getdata(String post, String name, String imagepath, String title, String datetime, String postid, final String email,int o) {

        postinfo current = new postinfo();
        if (o == 10) {

            current.email = email;
            current.title = title;
            current.imagepath = imagepath;
            current.post = post;
            current.name = name;
            current.datetime = datetime;
            current.postid = postid;
            data.add(current);

            adapter = new postrecycler(this, data);
            recyclerView.setAdapter(adapter);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(false);
              scroll();



        } else {
            if(data.size()!=j){
                if(data.size()!=no) {
                    current.email = email;
                    current.title = title;
                    current.imagepath = imagepath;
                    current.post = post;
                    current.name = name;
                    current.datetime = datetime;
                    current.postid = postid;
                    data.add(current);
                    adapter.setfilter(data);

                }
            }
            scroll();





           /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                    super.onScrolled(reyclerView, dx, dy);
                    if (!reyclerView.canScrollVertically(1)) {

                        if (j-10 <= no) {
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {

                                    j=j+10;

                                    postview(j, totalsize);

                                }
                            };
                            h.postDelayed(r, 1000);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        else{
                            progressBar.setVisibility(View.GONE);
                            // Toast.makeText(MainRnewsfeedActivity.this,"end ",Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                }
            });*/



            }

    }
public  void scroll(){

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
            super.onScrolled(reyclerView, dx, dy);

            if (!reyclerView.canScrollVertically(1)) {
               final int u=j;
                if (u-10 <= no) {
                    Handler h = new Handler();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {


                            j=u+10;
                           // Toast.makeText(MainRnewsfeedActivity.this,j+"",Toast.LENGTH_SHORT).show();

                            postview(j, totalsize);
                            progressBar.setVisibility(View.GONE);
                        }
                    };
                    h.postDelayed(r, 1000);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                   // Toast.makeText(MainRnewsfeedActivity.this,"end",Toast.LENGTH_SHORT).show();


                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);



        }


    });
    searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {

            ss.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.VISIBLE);
            int word=0;
            String[] c=new String[s.length()];int j=0;
            if(s.contains(" ")){

                for(int i=0;i<s.length();++i){
                    if (s.charAt(i)==' ') {

                        if(word==0){
                            c[word]=s.substring(0,i);
                            j=i;
                            word++;
                        }
                        else {
                            c[word]=s.substring(j,i);
                            j=i;
                            word++;
                        }

                    }
                }}

            // String[] m=s.split(" ");
            //search(c,word);
            Fragment=new searchFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.rre,Fragment,"f").commit();
            Bundle b = new Bundle();

            b.putString("words",s);
            b.putInt("size",word);
            Fragment.setArguments(b);
            //Intent i=new Intent(context,searchActivity.class);
            //i.putExtra("words",s);
            //  i.putExtra("size",word);
            // startActivity(i);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            // List<postinfo> filter= filter(data, s, 1);
            //      adapter.setfilter(filter);


            return true;
        }
    });
}


    public List<postinfo>filter(List<postinfo>filter,String query) {

        int size= filter.size();

        List<postinfo> filtered=new ArrayList<>();

        for(int i=0;i<size;i++) {
            String text=filter.get(i).post; String t=filter.get(i).title;
            if(text.contains(query)||text.startsWith(query)||text.endsWith(query)||t.contains(query)||t.startsWith(query)||t.endsWith(query)){
                filtered.add(filter.get(i));

            }
        }


        return filtered;
    }
    private void setupnavigation() {
        BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.bottomnavigation);
        new bottomnavigationviewhelper(context,bottomNavigationViewEx);
        new bottomnavigationviewhelper(bottomNavigationViewEx);

        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(false);

    }
    public void hintsshow() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST,hintsviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);


                       String hints = post.getString("hints");


                        getdata(hints);
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

    private void getdata( String hints) {

           f.add(hints);
        ArrayAdapter<String> d=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,f);
        ss.setAdapter(d);

        ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                t=(TextView)view; int word=0;
                ss.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                searchView.setQuery(t.getText().toString(),false);

                Fragment=new searchFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.rre,Fragment,"f").commit();
                Bundle b = new Bundle();

                b.putString("words",t.getText().toString());
                b.putInt("size",word);
                Fragment.setArguments(b);
            }
        });








    }
    public void usersshow() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST,usersviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                     // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);


                        name = post.getString("name");
                        imagepath = post.getString("profileimg");
                        title = post.getString("email");

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

                map.put("email", EmailShow.getText().toString());
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

       friendcaserecycler=new friendcaserecycler(this,d,0);
        rt.setAdapter(friendcaserecycler);
        rt.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rt.setHasFixedSize(false);rt.setNestedScrollingEnabled(false);


    }
  /*  private void postview(final int firs,final int las) {


            StringRequest request = new StringRequest(Request.Method.POST, postviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (j <=no){
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);


                        posttt = post.getString("post");
                        name = post.getString("name");
                        imagepath = post.getString("profileimg");
                        title = post.getString("title");
                        datetime=post.getString("date_time");
                        postid=post.getString("postid");
                        String e=post.getString("email");
                        getdata(posttt,name,imagepath,title,datetime,postid,e,first);
                        progressBar.setVisibility(View.GONE);

                        j= firs;
                   //     Toast.makeText(getApplicationContext(),firs,Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                        gifImageView.setVisibility(View.GONE);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }}
                else {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();

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

                map.put("first", String.valueOf(firs));
                map.put("last", String.valueOf(las));

                return map;

            }
        };
        requestQueue.add(request);

    }


    private void getdata(String post, String name, String imagepath, String title, String datetime, String postid, final String email,int o) {

        postinfo current = new postinfo();
        if (o == 0) {

            current.email = email;
            current.title = title;
            current.imagepath = imagepath;
            current.post = post;
            current.name = name;
            current.datetime = datetime;
            current.postid = postid;
            data.add(current);

            adapter = new postrecycler(this, data);
            recyclerView.setAdapter(adapter);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(false);


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                    super.onScrolled(reyclerView, dx, dy);
                    final int no = Integer.parseInt(String.valueOf(postno));

                    if (!reyclerView.canScrollVertically(1)) {

                        if (j <= no) {
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {

                                    first = j + 11;

                                  //  Toast.makeText(MainRnewsfeedActivity.this, first + " ", Toast.LENGTH_SHORT).show();
                                    postview(first, last);
                                    progressBar.setVisibility(View.GONE);
                                }
                            };
                            h.postDelayed(r, 1000);
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                           // Toast.makeText(MainRnewsfeedActivity.this, "end ", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                }


            });
            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    int word=0;
                    String[] c=new String[s.length()];int j=0;
                    if(s.contains(" ")){

                    for(int i=0;i<s.length();++i){
                        if (s.charAt(i)==' ') {

                            if(word==0){
                                c[word]=s.substring(0,i);
                                j=i;
                                word++;
                            }
                            else {
                                c[word]=s.substring(j,i);
                                j=i;
                                word++;
                            }

                        }
                    }}

                // String[] m=s.split(" ");
                    //search(c,word);
                    Fragment=new searchFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.rre,Fragment,"f").commit();
                    Bundle b = new Bundle();

                       b.putString("words",s);
                     b.putInt("size",word);
                    Fragment.setArguments(b);
                   //Intent i=new Intent(context,searchActivity.class);
                   //i.putExtra("words",s);
                  //  i.putExtra("size",word);
                  // startActivity(i);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                   // List<postinfo> filter= filter(data, s, 1);
                      //      adapter.setfilter(filter);


                    return true;
                }
            });
        } else {
                 if(data.size()!=first+11){
                current.email = email;
                current.title = title;
                current.imagepath = imagepath;
                current.post = post;
                current.name = name;
                current.datetime = datetime;
                current.postid = postid;
                data.add(current);
                     adapter.setfilter(data);


            }




            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                    super.onScrolled(reyclerView, dx, dy);
                    final int no = Integer.parseInt(String.valueOf(postno));
                    if (!reyclerView.canScrollVertically(1)) {

                        if (j <= no) {
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {

                                    first = j +11;


                                    postview(first, last);
                                }
                            };
                            h.postDelayed(r, 1000);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        else{
                            progressBar.setVisibility(View.GONE);
                           // Toast.makeText(MainRnewsfeedActivity.this,"end ",Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                }
            });}

    }
*/



}