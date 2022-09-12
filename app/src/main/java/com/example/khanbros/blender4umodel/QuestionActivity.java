package com.example.khanbros.blender4umodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionActivity extends AppCompatActivity {
    GifImageView gifImageView;GridView ss;ArrayList<String> ff=new ArrayList<>();
    Context context=QuestionActivity.this;String postcounturl="http://10.0.2.2:8080/program/questioncount.php";
    RelativeLayout r,r2,rl;CircleImageView circleImageView;
    String name,postid;BroadcastReceiver broadcastReceiver;int j=10,last=3,first;
    String title;SwipeRefreshLayout swipeRefreshLayout;
    String qusetion; questionrecycler  adapter;boolean isscrolling=false;
    String imagepath;String datetime; String hintsviewurl="http://10.0.2.2:8080/program/hintsview.php";
    RecyclerView recyclerView;  ArrayList<questionpostinfo> data=new ArrayList<>();
MaterialSearchView searchView;String postno;int no;
    EditText post;RequestQueue requestQueue;ImageLoader imageLoader;
    TextView EmailShow,t;FrameLayout f; String viewurl="http://10.0.2.2:8080/program/view.php";
    String questionviewurl="http://10.0.2.2:8080/program/questionview.php";
    String EmailHolder;SharedPreferences sharedPreferences;ProgressBar progressBar;
        int totalsize;String res1,res2,res3;

    RecyclerView recyclerView2;LinearLayoutManager linearLayoutManager;
    String searchpostviewurl="http://10.0.2.2:8080/program/search2.php";

    String words;int size;ArrayList<questionpostinfo> data2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        r= (RelativeLayout) findViewById(R.id.r);
        rl= (RelativeLayout) findViewById(R.id.rl);
        r2= (RelativeLayout) findViewById(R.id.rl2);
        f= (FrameLayout) findViewById(R.id.f);
        isconnected();
        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        declare();



        setupnavigation();postcount();
        showvolley();
    }
    public void isconnected()
    {
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(isconnect()==true){
                    if( r.getVisibility()==View.VISIBLE){

                    }else{
                        r.setVisibility(View.VISIBLE);
                        r2.setVisibility(View.VISIBLE);rl.setVisibility(View.GONE);
                        Snackbar.make(r," INTERNET IS CONNECTED",Snackbar.LENGTH_SHORT).show();
                    }}
                else {
                    r.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    f.setVisibility(View.GONE);
                    Snackbar.make(r,"NO INTERNET CONNECTION",Snackbar.LENGTH_INDEFINITE).show();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
    private void declare() {
        ss=findViewById(R.id.ss);
        gifImageView=findViewById(R.id.i);
        try {
            InputStream inputStream=getAssets().open("blender.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout=findViewById(R.id.s);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();

                j=10;
                postview(j,totalsize);

            }
        });

        EmailShow = (TextView)findViewById(R.id.EmailShow);
        post= (EditText) findViewById(R.id.post);
       progressBar=findViewById(R.id.progressBar7);

        circleImageView= (CircleImageView) findViewById(R.id.imageView);
        sharedPreferences=getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        EmailHolder=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        EmailShow.setText(EmailHolder);
        recyclerView= (RecyclerView) findViewById(R.id.re);
        recyclerView2= (RecyclerView) findViewById(R.id.rre);
        searchView= (MaterialSearchView) findViewById(R.id.search);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                recyclerView.setVisibility(View.GONE);

                r2.setVisibility(View.GONE);
                data2.clear();ff.clear();
                ss.setVisibility(View.VISIBLE);
                hintsshow();




            }

            @Override
            public void onSearchViewClosed() {
                ss.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionFragment questionFragment=new questionFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.f,questionFragment,"f").commit();
                r.setVisibility(View.INVISIBLE);
                r2.setVisibility(View.INVISIBLE);
                Bundle b = new Bundle();

                b.putString("email",EmailShow.getText().toString());
                b.putString("no","2");
                questionFragment.setArguments(b);
            }
        });

    }

    private void setupnavigation() {
        BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.bottomnavigation);
        new bottomnavigationviewhelper(context,bottomNavigationViewEx);
        new bottomnavigationviewhelper(bottomNavigationViewEx);

        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(false);
    }

    private void postcount() {

        requestQueue= Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST,postcounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                postno=response;
                no=Integer.parseInt(postno);
                totalsize=no;

                postview(j,totalsize);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

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


                                if(profileimg.startsWith("http")||profileimg.startsWith("con")){
                                    imageLoader.displayImage(profileimg,circleImageView,options);
                                }
                                else {
                                    imageLoader.displayImage(photo,circleImageView,options);
                                }
                                // Picasso.get().load(photo).into(imageView);
                              // showquestion();
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
        requestQueue= Volley.newRequestQueue(getApplicationContext());
            if(j==10){

        StringRequest request = new StringRequest(Request.Method.POST, questionviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res1=response;
                int i,y;
                        int o=k;
                if (o-10 <=no){
                    try {
                        JSONObject res=new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");

                        y=size-k;
                        i=y+9;
                        // Toast.makeText(getApplicationContext(),no+"",Toast.LENGTH_LONG).show();


                        while (i>=y){
                            JSONObject post = posts.getJSONObject(i);

                            qusetion = post.getString("question");
                            name = post.getString("name");
                            imagepath = post.getString("profileimg");
                            title = post.getString("title");
                            datetime=post.getString("date_time");
                            postid=post.getString("postid");
                            String e=post.getString("email");

                            getdata(qusetion,name,imagepath,title,datetime,postid,e,k);
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

                            qusetion = post.getString("question");
                            name = post.getString("name");
                            imagepath = post.getString("profileimg");
                            title = post.getString("title");
                            datetime=post.getString("date_time");
                            postid=post.getString("postid");
                            String e=post.getString("email");

                            getdata(qusetion,name,imagepath,title,datetime,postid,e,k);
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

                    //   Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();

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
                int o=k;
                if (o-10 <=no){
                    try {
                        JSONObject res=new JSONObject(res1);
                        JSONArray posts = res.getJSONArray("post");

                        y=size-k;
                        i=y+9;
                        // Toast.makeText(getApplicationContext(),no+"",Toast.LENGTH_LONG).show();


                        while (i>=y){
                            JSONObject post = posts.getJSONObject(i);

                            qusetion = post.getString("question");
                            name = post.getString("name");
                            imagepath = post.getString("profileimg");
                            title = post.getString("title");
                            datetime=post.getString("date_time");
                            postid=post.getString("postid");
                            String e=post.getString("email");

                            getdata(qusetion,name,imagepath,title,datetime,postid,e,k);
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

                    //   Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();

                }
            }

    }


    private void getdata(String post, String name, String imagepath, String title, String datetime, String postid, final String email,int o) {

        questionpostinfo current = new questionpostinfo();
        if (o == 10) {

            current.email = email;
            current.title = title;
            current.imagepath = imagepath;
            current.question = post;
            current.name = name;
            current.datetime = datetime;
            current.postid = postid;
            data.add(current);

            adapter = new questionrecycler(context, data,"0");
            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(false);
            scroll();


        } else {

            if (data.size() != j) {
                if(data.size()!=no){
                    current.email = email;
                    current.title = title;
                    current.imagepath = imagepath;
                    current.question = post;
                    current.name = name;
                    current.datetime = datetime;
                    current.postid = postid;
                    data.add(current);
                    adapter.setfilter(data);
                }



            }
            scroll();

        }

    }

    public void scroll() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                super.onScrolled(reyclerView, dx, dy);


                if (!reyclerView.canScrollVertically(1)) {
                    final   int l=j;
                    if (l-10 <= no) {
                        Handler h = new Handler();
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                j=l+10;
                                //   Toast.makeText(context, ""+j, Toast.LENGTH_SHORT).show();
                                postview(j, totalsize);
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
                data2.clear();
                recyclerView2.setVisibility(View.VISIBLE);
                ss.setVisibility(View.GONE);
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

                search(s,word);
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

        ff.add(hints);
        ArrayAdapter<String> d=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,ff);
        ss.setAdapter(d);

        ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                t=(TextView)view; int word=0;
                ss.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                searchView.setQuery(t.getText().toString(),false);

                search(t.getText().toString(), word);
            }
        });








    }

    /* private void postview(final int firs,final int las) {


            StringRequest request = new StringRequest(Request.Method.POST, questionviewurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (j <=no){
                        try {
                            JSONObject res=new JSONObject(response);
                            JSONArray posts = res.getJSONArray("post");

                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.getJSONObject(i);


                                qusetion = post.getString("question");
                                name = post.getString("name");
                                imagepath = post.getString("profileimg");
                                title = post.getString("title");
                                datetime=post.getString("date_time");
                                postid=post.getString("postid");
                                String e=post.getString("email");

                                getdata(qusetion,name,imagepath,title,datetime,postid,e,first);
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

                    map.put("first", String.valueOf(firs));
                    map.put("last", String.valueOf(las));

                    return map;

                }
            };
            requestQueue.add(request);

        }


        private void getdata(String post, String name, String imagepath, String title, String datetime, String postid, final String email,int o) {

           questionpostinfo current = new questionpostinfo();
            if (o == 0) {

                current.email = email;
                current.title = title;
                current.imagepath = imagepath;
                current.question = post;
                current.name = name;
                current.datetime = datetime;
                current.postid = postid;
                data.add(current);

                adapter = new questionrecycler(context, data,"0");
                recyclerView.setAdapter(adapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(false);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                        super.onScrolled(reyclerView, dx, dy);
                        final int no = Integer.parseInt(String.valueOf(postno));

                        if (!reyclerView.canScrollVertically(1))  {

                            if (j <= no) {
                                Handler h = new Handler();
                                Runnable r = new Runnable() {
                                    @Override
                                    public void run() {

                                        first = j +3;

                                      //  Toast.makeText(context,first+" ",Toast.LENGTH_SHORT).show();
                                        postview(first, last);               progressBar.setVisibility(View.GONE);
                                    }
                                };
                                h.postDelayed(r, 1000);
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                              //  Toast.makeText(context,"end ",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            isscrolling = true;
                        }
                    }


                });
                searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {

                        data2.clear();
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

                        search(s,word);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {




                        return true;
                    }
                });
            } else {
                if(data.size()!=first+3){
                    current.email = email;
                    current.title = title;
                    current.imagepath = imagepath;
                    current.question = post;
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

                                        first = j +3;


                                        postview(first, last);
                                    }
                                };
                                h.postDelayed(r, 1000);
                                progressBar.setVisibility(View.VISIBLE);
                            }

                            else{
                                progressBar.setVisibility(View.GONE);
                               // Toast.makeText(context,"end ",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            isscrolling = true;
                        }
                    }
                });
            }
        }*/
    public void search(final String words,final int size) {


        StringRequest request = new StringRequest(Request.Method.POST, searchpostviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);

                        qusetion = post.getString("question");
                        name = post.getString("name");
                        imagepath = post.getString("profileimg");
                        title = post.getString("title");
                        datetime=post.getString("date_time");
                        postid=post.getString("postid");
                        String e=post.getString("email");

                    data(qusetion,name,imagepath,title,datetime,postid,e);

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

                map.put("size", String.valueOf(size));

                map.put("words", words);

                return map;

            }
        };
        requestQueue.add(request);

    }

    public void data(String post, String name, String imagepath, String title, String datetime, String postid, final String email) {
        questionpostinfo curren = new questionpostinfo();


            curren.email = email;
            curren.title = title;
            curren.imagepath = imagepath;
            curren.question = post;
            curren.name = name;
            curren.datetime = datetime;curren.postid = postid;
            data2.add(curren);

            adapter = new questionrecycler(context, data2,"0");
            recyclerView2.setAdapter(adapter);

            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            recyclerView2.setHasFixedSize(false);

    }
public List<questionpostinfo>filter(List<questionpostinfo>filter,String query){
       int size= filter.size();

        List<questionpostinfo> filtered=new ArrayList<>();

            for(int i=0;i<size;i++) {
                String text=filter.get(i).question; String t=filter.get(i).title;
                if(text.contains(query)||text.startsWith(query)||text.endsWith(query)||t.contains(query)||t.startsWith(query)||t.endsWith(query)){
                    filtered.add(filter.get(i));

                }
            }






        return filtered;
}
}

