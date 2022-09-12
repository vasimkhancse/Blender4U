package com.example.khanbros.blender4umodel;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
public class videoFragment extends Fragment {

    String name,imagepath,title,email;SharedPreferences sharedPreferences;
RequestQueue requestQueue;RecyclerView recyclerView;
    ArrayList<showcase> data=new ArrayList<>();     String postviewurl = "http://10.0.2.2:8080/program/userpostviewvideo.php";
    public videoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_video, container, false);
        sharedPreferences=getActivity().getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        recyclerView=v.findViewById(R.id.rt);
        if(getArguments().getString("no").equals("0")){
            email=getArguments().getString("email");
        }
       showquestion();
       return v;
    }
    public void showquestion() {
        requestQueue = Volley.newRequestQueue(getActivity());



        StringRequest request = new StringRequest(Request.Method.POST, postviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);



                       String posttt = post.getString("post");

                      String  postid = post.getString("postid");
                        getdata(posttt, postid);


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

    private void getdata(String post, String postid) {
        showcase current = new showcase();


        current.post = post;

        current.postid = postid;
        data.add(current);
        Collections.reverse(data);
       showcaserecycler adapter = new showcaserecycler(getActivity(), data," ");
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //   staggeredGridLayoutManager.setMeasuredDimension(400,200);
        //  staggeredGridLayoutManager.setSpanCount(3);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager.setGapStrategy(2);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

    }
}
