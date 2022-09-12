package com.example.khanbros.blender4umodel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class learnActivity extends AppCompatActivity {
    Context context = learnActivity.this;
    String apikey = "AIzaSyA1RL827iT-sShARwgwdK-_ZwTG0sqieLI";
    TextView EmailShow;
   MaterialSearchView searchView;             String title[]={"keys","Tutorials","Blender Updates"};

    Button tutorial ;RelativeLayout relativeLayout,rr,r2,rl;BroadcastReceiver broadcastReceiver;
    RequestQueue requestQueue;ProgressBar progressBar;
    String EmailHolder;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<youtubedata> data = new ArrayList<>();
    youtubedata data2;ListView l;
    String link = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=viewCount&q=tex&type=video&videoDefinition=high&key=AIzaSyA1RL827iT-sShARwgwdK-_ZwTG0sqieLI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_learn);
        relativeLayout=findViewById(R.id.r);
        r2=findViewById(R.id.rl2);
        rl=findViewById(R.id.rl);rr=findViewById(R.id.rr);
        isconnected();
        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
 declare();

        setupnavigation();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
    public void declare(){




        progressBar=findViewById(R.id.progressBar);
        l=findViewById(R.id.l);

        l.setAdapter(new list(context,title));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position==0){
                   startActivity(new Intent(context,keyActivity.class));
               }
                if(position==1){
                   startActivity(new Intent(context,tutorialActivity.class));
                }
                if(position==2){
                    startActivity(new Intent(context,updateActivity.class));
                }
            }
        });
        EmailShow = (TextView) findViewById(R.id.EmailShow);

        recyclerView = (RecyclerView) findViewById(R.id.re);
        sharedPreferences = getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        EmailHolder = sharedPreferences.getString(getResources().getString(R.string.email), "");
        EmailShow.setText(EmailHolder);


        searchView = (MaterialSearchView) findViewById(R.id.search);


      searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                data.clear();
                recyclerView.setVisibility(View.VISIBLE);

               l.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                data.clear();
               l.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                recyclerView.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                progressBar.setVisibility(View.VISIBLE);
                data.clear();
                show(text);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                data.clear();
                return false;
            }
        });

      init(data);
    }
    public void isconnected()
    {
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(isconnect()==true){
                    if( relativeLayout.getVisibility()==View.VISIBLE){

                    }else{
                        relativeLayout.setVisibility(View.VISIBLE);rr.setVisibility(View.VISIBLE);
                        r2.setVisibility(View.VISIBLE);rl.setVisibility(View.GONE);
                        Snackbar.make(relativeLayout," INTERNET IS CONNECTED",Snackbar.LENGTH_SHORT).show();
                    }}
                else {
                    relativeLayout.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);rr.setVisibility(View.GONE);
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

   private void show(String text) {
         text=text.replace(" ","+");
        String link = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+text+"&type=video&maxResults=50&key=AIzaSyA1RL827iT-sShARwgwdK-_ZwTG0sqieLI";
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                data = parsevideo(response);
                  init(data);
                progressBar.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        requestQueue.add(jsonObjectRequest);
    }

    private void init(ArrayList<youtubedata> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        youtuberecycler youtuberecycler = new youtuberecycler(data, context,0);
        recyclerView.setAdapter(youtuberecycler);
        youtuberecycler.notifyDataSetChanged();
    }

    private ArrayList<youtubedata> parsevideo(JSONObject response) {

        if (response.has("items")) {
            try {
                JSONArray posts = response.getJSONArray("items");

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject jsonObject = posts.getJSONObject(i);
                    if (jsonObject.has("id")) {
                        JSONObject id = jsonObject.getJSONObject("id");
                        if (id.has("kind")) {
                            if (id.getString("kind").equals("youtube#video")) {


                                JSONObject snipet = jsonObject.getJSONObject("snippet");
                                String title = snipet.getString("title");

                                String desc = snipet.getString("description");

                                String img = snipet.getJSONObject("thumbnails").getJSONObject("high").getString("url");
                                String iid = jsonObject.getJSONObject("id").getString("videoId");
                            //    Toast.makeText(getApplicationContext(),desc,Toast.LENGTH_LONG).show();


                                data2 = new youtubedata();
                                data2.setDesc(desc);
                                data2.setImage(img);
                                data2.setTitle(title);
                                data2.setId(iid);
                                data.add(data2);

                            }
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
            return data;


        }


        private void setupnavigation () {
            BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomnavigation);
            new bottomnavigationviewhelper(context, bottomNavigationViewEx);
            new bottomnavigationviewhelper(bottomNavigationViewEx);
            Menu menu = bottomNavigationViewEx.getMenu();
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(false);

        }


        public class list extends ArrayAdapter{

              String[] title;
             int img[]={R.drawable.ic_keys,R.drawable.ic_learn,R.drawable.ic_update};
            public list( Context context,String[] title) {

                super(context, R.layout.options,R.id.textView,title);
                this.title=title;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater layoutInflater= (LayoutInflater)context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v=layoutInflater.inflate(R.layout.options,parent,false);

                TextView g=v.findViewById(R.id.textView);
               CircleImageView i=v.findViewById(R.id.imageView);
                i.setImageResource(img[position]);
                g.setText("  "+title[position]);



                return  v;
            }
        }
    }
