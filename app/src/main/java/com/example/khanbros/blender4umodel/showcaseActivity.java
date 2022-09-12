package com.example.khanbros.blender4umodel;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.os.Handler;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.nostra13.universalimageloader.core.ImageLoader;

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


public class showcaseActivity extends AppCompatActivity {
    Context context = showcaseActivity.this;
    TextView EmailShow,t,count;
    String postno;
   String text="",res1,res2,res3;
    String selectedPath;
    int j=10, no2;

    String EmailHolder;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    String posttt;
    showcaserecycler adapter;
    int no;
    boolean isscrolling = false;
    SwipeRefreshLayout swipeRefreshLayout;
    String imagepath;
    RelativeLayout relativeLayout, r2, rl;
    BroadcastReceiver broadcastReceiver;
    Button LogOut;TextView count2;
    RecyclerView recyclerView;
    ArrayList<showcase> data = new ArrayList<>();
    MaterialSearchView searchView;
    String postid;
    ArrayList<showcase> data2 = new ArrayList<>();

    RecyclerView recyclerView2;
    LinearLayoutManager linearLayoutManager;
    String searchpostviewurl = "http://10.0.2.2:8080/program/search.php";
    String name, datetime, title;
    RequestQueue requestQueue;
    String words="";GridView ss;
    int size;  String hintsviewurl="http://10.0.2.2:8080/program/hintsview.php";
    String postviewurl = "http://10.0.2.2:8080/program/postview2.php";
    String postviewurl3 = "http://10.0.2.2:8080/program/date.php";
    String postviewurl2 = "http://10.0.2.2:8080/program/userssort.php";
    String postcounturl = "http://10.0.2.2:8080/program/showcasecount.php";
    String postcounturl2 = "http://10.0.2.2:8080/program/countuserssort.php";
    String postcounturl3 = "http://10.0.2.2:8080/program/countdate.php";

    ImageLoader imageLoader; ArrayList<String> f=new ArrayList<>();
    GifImageView gifImageView;FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcase);

        relativeLayout = findViewById(R.id.r);
        r2 = findViewById(R.id.rl2);
        rl = findViewById(R.id.rl);
        isconnected();
        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        declare();
        setupnavigation();
        // showquestion();



        postcount();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.filter){
           final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            View v=getLayoutInflater().inflate(R.layout.filter,null);


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
                    getSupportActionBar().setTitle(words);

                    spinner();
                }
            });

            Spinner spinner=v.findViewById(R.id.spinner);
            String[] a={"Filter","High LIKES","public","trend"};
            ArrayAdapter<String> s=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,a);
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

            alertDialog.setView(v);
            AlertDialog dialog=alertDialog.create();
            dialog.show();


        }
        return super.onOptionsItemSelected(item);
    }
public  void spinner(){
      if(text.equals("public")){
          data.clear();
          gifImageView.setVisibility(View.VISIBLE);
          gifImageView.startAnimation();
          j=10;
          postcount();
      }
      if(text.equals("High LIKES")){
          data.clear();
          gifImageView.setVisibility(View.VISIBLE);
          gifImageView.startAnimation();
          j = 10;
          postcount2();
      }
    if(text.equals("trend")){
        data.clear();
        gifImageView.setVisibility(View.VISIBLE);
        gifImageView.startAnimation();
        j = 10;
        postcount3();
    }
}
    public void declare() {
        ss=findViewById(R.id.ss);
        count=findViewById(R.id.count);
        count2=findViewById(R.id.count2);
        gifImageView = findViewById(R.id.i);
        try {
            InputStream inputStream = getAssets().open("blender.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout = findViewById(R.id.s);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(text.equals("public")){
                    data.clear();
                    j=10;
                    gifImageView.setVisibility(View.VISIBLE);
                    gifImageView.startAnimation();
                    postcount();
                }
              else   if(text.equals("trend")){
                    data.clear();
                    j = 10;
                    gifImageView.setVisibility(View.VISIBLE);
                    gifImageView.startAnimation();
                    postcount3();
                }
              else   if(text.equals("High LIKES")){
                    data.clear();
                    j = 10;
                    gifImageView.setVisibility(View.VISIBLE);
                    gifImageView.startAnimation();
                    postcount2();
                }
                else {
                    gifImageView.setVisibility(View.VISIBLE);
                    data.clear();
                        gifImageView.startAnimation();
                        j=10;
                        postcount();

                }
            }
        });
         floatingActionButton=findViewById(R.id.f);
         floatingActionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context,postActivity.class);
                 intent.putExtra("email",EmailShow.getText().toString());
                 intent.putExtra("no","2");
                 startActivity(intent);
             }
         });
        recyclerView = (RecyclerView) findViewById(R.id.re);
        recyclerView2 = (RecyclerView) findViewById(R.id.rre);
        EmailShow = (TextView) findViewById(R.id.EmailShow);
        searchView = (MaterialSearchView) findViewById(R.id.search);
        sharedPreferences = getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        EmailHolder = sharedPreferences.getString(getResources().getString(R.string.email), "");
        EmailShow.setText(EmailHolder);
        progressBar = findViewById(R.id.progressBar7);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                recyclerView.setVisibility(View.GONE);

                r2.setVisibility(View.GONE);

                data2.clear();f.clear();
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

    }

    public void isconnected() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isconnect() == true) {
                    if (relativeLayout.getVisibility() == View.VISIBLE) {

                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        r2.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        Snackbar.make(relativeLayout, " INTERNET IS CONNECTED", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    Snackbar.make(relativeLayout, "NO INTERNET CONNECTION", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public boolean isconnect() {
        ConnectivityManager c = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = c.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = c.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = c.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
    public void postcount3() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST,postviewurl3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                  JSONObject  res = new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");
                    no=posts.length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  no=Integer.parseInt(count2.getText().toString());

              // Toast.makeText(getApplicationContext(), no +" ", Toast.LENGTH_LONG).show();


                postview3(j,no);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;


        requestQueue.add(request);

    }
    public void postcount2() {

        requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest request = new StringRequest(Request.Method.POST,postcounturl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                   count.setText("");
                   postno=response;
                 char[] c=response.toCharArray();
                 for(int i=0;i<c.length/2;i++){
                     count.append(c[i]+"");
                 }
               no=Integer.parseInt(count.getText().toString());

              //  Toast.makeText(getApplicationContext(), no +" ", Toast.LENGTH_LONG).show();

                postview2(j,no);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;


        requestQueue.add(request);

    }
    public void postcount() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST,postcounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                postno=response;
                no=Integer.parseInt(postno);


                postview(j,no);
             // Toast.makeText(getApplicationContext(), no + "", Toast.LENGTH_LONG).show();

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
    private void postview3(final int k, final int size) {

        if(j==10){
            StringRequest request = new StringRequest(Request.Method.POST, postviewurl3, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    res3=response;

                    if (k <= size&&size>=9 ) {
                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray posts = res.getJSONArray("post");


                            int i,f;
                            i=k-10;
                            f=i;


                            for( i=f;i<k;i++) {
                                JSONObject post = posts.getJSONObject(i);


                                posttt = post.getString("post");

                                postid = post.getString("postid");
                                String t = post.getString("title");
                                getdata(posttt, postid, t,k);
                                progressBar.setVisibility(View.GONE);

                                swipeRefreshLayout.setRefreshing(false);
                                gifImageView.setVisibility(View.GONE);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    else if(size<=9) {

                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray posts = res.getJSONArray("post");


                            for (int p = 0; p < posts.length(); p++) {
                                JSONObject post = posts.getJSONObject(p);

                                posttt = post.getString("post");

                                postid = post.getString("postid");
                                String t = post.getString("title");
                                getdata(posttt, postid, t,k);
                                progressBar.setVisibility(View.GONE);

                                swipeRefreshLayout.setRefreshing(false);
                                gifImageView.setVisibility(View.GONE);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
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

            if (k <= size && size >= 9) {
                try {
                    JSONObject res = new JSONObject(res3);
                    JSONArray posts = res.getJSONArray("post");


                    int i, f;
                    i = k - 10;
                    f = i;


                    for (i = f; i < k; i++) {
                        JSONObject post = posts.getJSONObject(i);


                        posttt = post.getString("post");

                        postid = post.getString("postid");
                        String t = post.getString("title");
                        getdata(posttt, postid, t, k);
                        progressBar.setVisibility(View.GONE);

                        swipeRefreshLayout.setRefreshing(false);
                        gifImageView.setVisibility(View.GONE);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                progressBar.setVisibility(View.GONE);

                //   Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();

            }

        }}
    private void postview2(final int k, final int size) {

          if(j==10){
        StringRequest request = new StringRequest(Request.Method.POST, postviewurl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  res2=response;

                if (k <= size&&size>=9 ) {
                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");


                       int i,f;
                       i=k-10;
                       f=i;


                        for( i=f;i<k;i++) {
                            JSONObject post = posts.getJSONObject(i);


                            posttt = post.getString("post");

                            postid = post.getString("postid");
                            String t = post.getString("title");
                            getdata(posttt, postid, t,k);
                            progressBar.setVisibility(View.GONE);

                            swipeRefreshLayout.setRefreshing(false);
                            gifImageView.setVisibility(View.GONE);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else if(size<=9) {

                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");


                        for (int p = 0; p < posts.length(); p++) {
                            JSONObject post = posts.getJSONObject(p);

                            posttt = post.getString("post");

                            postid = post.getString("postid");
                            String t = post.getString("title");
                            getdata(posttt, postid, t,k);
                            progressBar.setVisibility(View.GONE);

                            swipeRefreshLayout.setRefreshing(false);
                            gifImageView.setVisibility(View.GONE);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
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

              if (k <= size&&size>=9 ) {
                  try {
                      JSONObject res = new JSONObject(res2);
                      JSONArray posts = res.getJSONArray("post");


                      int i,f;
                      i=k-10;
                      f=i;


                      for( i=f;i<k;i++) {
                          JSONObject post = posts.getJSONObject(i);


                          posttt = post.getString("post");

                          postid = post.getString("postid");
                          String t = post.getString("title");
                          getdata(posttt, postid, t,k);
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
          }


    private void postview(final int k, final int size) {

       if(j==10){
        StringRequest request = new StringRequest(Request.Method.POST, postviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res1=response;
                int i, y;
                   int o=k;
                if (o - 10 <= no) {
                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");

                        y = size - k;
                        i = y + 9;


                        while (i >= y) {
                            JSONObject post = posts.getJSONObject(i);


                            posttt = post.getString("post");

                            postid = post.getString("postid");
                            String t = post.getString("title");
                            getdata(posttt, postid, t,k);
                            progressBar.setVisibility(View.GONE);

                            swipeRefreshLayout.setRefreshing(false);
                            gifImageView.setVisibility(View.GONE);
                            i--;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else if(no<=9) {

                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray posts = res.getJSONArray("post");


                        for (int p = 0; p < posts.length(); p++) {
                            JSONObject post = posts.getJSONObject(p);

                            posttt = post.getString("post");

                            postid = post.getString("postid");
                            String t = post.getString("title");
                            getdata(posttt, postid, t,k);
                            progressBar.setVisibility(View.GONE);

                            swipeRefreshLayout.setRefreshing(false);
                            gifImageView.setVisibility(View.GONE);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
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
           int i, y;
           int o=k;
           if (o - 10 <= no) {
               try {
                   JSONObject res = new JSONObject(res1);
                   JSONArray posts = res.getJSONArray("post");

                   y = size - k;
                   i = y + 9;


                   while (i >= y) {
                       JSONObject post = posts.getJSONObject(i);


                       posttt = post.getString("post");

                       postid = post.getString("postid");
                       String t = post.getString("title");
                       getdata(posttt, postid, t,k);
                       progressBar.setVisibility(View.GONE);

                       swipeRefreshLayout.setRefreshing(false);
                       gifImageView.setVisibility(View.GONE);
                       i--;

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

    }


    private void getdata(String post, String postid, String title, int o) {

        showcase current = new showcase();
        if (o == 10) {

            current.post = post;
            current.title = title;

            current.postid = postid;
            data.add(current);

            adapter = new showcaserecycler(context, data, "1");
            recyclerView.setAdapter(adapter);
       StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

            recyclerView.setHasFixedSize(true);
            staggeredGridLayoutManager.setGapStrategy(2);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            scroll();

        } else {

            if (data.size() != j) {
                if(data.size()!=no) {
                    current.title = title;

                    current.post = post;

                    current.postid = postid;
                    data.add(current);
                    adapter.setfilter(data);
                }

            }
            scroll();

        }

    }
public  void scroll (){
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
            super.onScrolled(reyclerView, dx, dy);


            if (!reyclerView.canScrollVertically(1)) {

                if(text.equals("High LIKES")||text.equals("trend")){
                    final  int u=j;
                    if (u <= no&&no>=9) {
                        Handler h = new Handler();
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                j = u + 10;
                                 // Toast.makeText(context, j+"", Toast.LENGTH_SHORT).show();
                                postview2(j, no);
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
                else {
                    final  int u=j;
                    if (u-10 <= no) {
                        Handler h = new Handler();
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                j = u + 10;
                                //  Toast.makeText(context, j+"", Toast.LENGTH_SHORT).show();
                                postview(j,no);
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
            data2.clear();
            int word = 0;
            String[] c = new String[s.length()];
            int j = 0;
            if (s.contains(" ")) {

                for (int i = 0; i < s.length(); ++i) {
                    if (s.charAt(i) == ' ') {

                        if (word == 0) {
                            c[word] = s.substring(0, i);
                            j = i;
                            word++;
                        } else {
                            c[word] = s.substring(j, i);
                            j = i;
                            word++;
                        }

                    }
                }
            }

            search(s, word);
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

                search(t.getText().toString(), word);
            }
        });








    }
    public void search(final String words, final int size) {


        StringRequest request = new StringRequest(Request.Method.POST, searchpostviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject res = new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);

                        posttt = post.getString("post");

                        postid = post.getString("postid");
                        String t = post.getString("title");
                        data(posttt, postid, t);


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

    public void data(String posttt, String postid, String title) {
        showcase current = new showcase();


        current.post = posttt;
        current.title = title;

        current.postid = postid;
        data2.add(current);


        showcaserecycler adapter = new showcaserecycler(context, data2, "1");


        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView2.setHasFixedSize(false);
        recyclerView2.setAdapter(adapter);


    }


    public List<showcase> filter(List<showcase> filter, String query) {
        int size = filter.size();

        List<showcase> filtered = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String text = filter.get(i).post;
            String t = filter.get(i).title;
            if (text.contains(query) || text.startsWith(query) || text.endsWith(query) || t.contains(query) || t.startsWith(query) || t.endsWith(query)) {
                filtered.add(filter.get(i));

            }
        }

        return filtered;
    }

    private void setupnavigation() {
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomnavigation);
        new bottomnavigationviewhelper(context, bottomNavigationViewEx);
        new bottomnavigationviewhelper(bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(false);
    }

}
  /*
      private void postview(final int firs,final int las) {


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

                            postid=post.getString("postid");
                            String t=post.getString("title");
                            getdata(posttt,postid,t,first);
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

   private void getdata(String post,  String postid,  String title,int o) {

       showcase current = new showcase();
        if (o == 0) {

          current.post=post;
            current.title = title;

            current.postid = postid;
            data.add(current);

            adapter = new showcaserecycler(context, data,"1");
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            recyclerView.setHasFixedSize(true);
            staggeredGridLayoutManager.setGapStrategy(2);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                    super.onScrolled(reyclerView, dx, dy);


                    if (!reyclerView.canScrollVertically(1))  {

                        if (j <= no) {
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {

                                    first = j +8;

                                 //   Toast.makeText(context,first+" ",Toast.LENGTH_SHORT).show();
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
            if(data.size()!=first+8){

                current.title = title;

                current.post=post;

                current.postid = postid;
                data.add(current);
                adapter.setfilter(data);
            }




            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView reyclerView, int dx, int dy) {
                    super.onScrolled(reyclerView, dx, dy);


                    if (!reyclerView.canScrollVertically(1)) {

                        if (j <= no) {
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {

                                    first = j +8;


                                    postview(first, last);
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
        }
    }*/

