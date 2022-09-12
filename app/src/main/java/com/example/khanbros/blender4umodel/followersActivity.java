package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class followersActivity extends AppCompatActivity {
MaterialSearchView searchView;TabLayout tabLayout;ViewPager viewPager;
    ArrayList<friendpostinfo> d=new ArrayList<>();  String searchpostviewurl = "http://10.0.2.2:8080/program/search3.php";
pageradapter pageradapter;RecyclerView recyclerView;RequestQueue requestQueue;
SharedPreferences sharedPreferences;String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        searchView=findViewById(R.id.search);
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.v);
        recyclerView=findViewById(R.id.r);


        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                tabLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                d.clear();
            }

            @Override
            public void onSearchViewClosed() {
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                d.clear();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                d.clear();
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


                return true;
            }
        });

       // tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        if(getIntent().getStringExtra("no").equals("0")){
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_public).setText("Public"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("following"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("followers"));

            pageradapter=new pageradapter(getSupportFragmentManager(),tabLayout.getTabCount(),0,"");


        }
        else{
           String e= getIntent().getStringExtra("email");
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("following"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("followers"));

            pageradapter=new pageradapter(getSupportFragmentManager(),tabLayout.getTabCount(),1,e);
        }

        viewPager.setAdapter(pageradapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    tabLayout.setBackgroundColor(Color.parseColor("#32222f"));
                }
                if(tab.getPosition()==1){
                    tabLayout.setBackgroundColor(Color.GRAY);
                }
                if(tab.getPosition()==2){
                    tabLayout.setBackgroundColor(Color.DKGRAY);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public void search(final String words, final int size) {

        requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, searchpostviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);


                      String  name = post.getString("name");
                      String  imagepath = post.getString("profileimg");
                      String  title = post.getString("email");

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

                map.put("size", String.valueOf(size));

                map.put("email",words);

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

       friendcaserecycler friendcaserecycler=new friendcaserecycler(this,d,2);
        recyclerView.setAdapter(friendcaserecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(false);recyclerView.setNestedScrollingEnabled(false);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
}
