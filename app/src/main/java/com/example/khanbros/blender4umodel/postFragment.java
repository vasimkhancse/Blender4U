package com.example.khanbros.blender4umodel;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class postFragment extends Fragment {
    String name,imagepath,title,email;SharedPreferences sharedPreferences;
    RequestQueue requestQueue;friendcaserecycler friendcaserecycler;RecyclerView rt;
    ArrayList<postinfo> d=new ArrayList<>();   String usersviewurl="http://10.0.2.2:8080/program/upost.php";

    public postFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_postt, container, false);
        sharedPreferences=getActivity().getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        rt=v.findViewById(R.id.rt);
        usersshow();
        if(getArguments().getString("no").equals("0")){
            email=getArguments().getString("email");
        }
        return v;
    }
    public void usersshow() {
        requestQueue= Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST,usersviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);

                      String  posttt = post.getString("post");
                        name = post.getString("name");
                        imagepath = post.getString("profileimg");
                        title = post.getString("title");
                      String  datetime=post.getString("date_time");
                      String  postid=post.getString("postid");
                        String e=post.getString("email");

                        if(posttt.endsWith("jpg")||posttt.endsWith("png")||posttt.endsWith("jpeg")){

                        }
                        else if(posttt.endsWith("mp4")||posttt.endsWith("3gp")){


                        }
                        else if(posttt.startsWith("https://youtu.be")){



                        }
                        else{
                            getdata(posttt,name,imagepath,title,datetime,postid,e);

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





    private void getdata(String post, String name, String imagepath, String title, String datetime, String postid, final String email) {

        postinfo current = new postinfo();


            current.email = email;
            current.title = title;
            current.imagepath = imagepath;
            current.post = post;
            current.name = name;
            current.datetime = datetime;
            current.postid = postid;
            d.add(current);
        Collections.reverse(d);
           postrecycler adapter = new postrecycler(getActivity(), d);
            rt.setAdapter(adapter);
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rt.setLayoutManager(linearLayoutManager);
            rt.setHasFixedSize(false);

        }}