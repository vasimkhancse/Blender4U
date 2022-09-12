package com.example.khanbros.blender4umodel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchFragment extends Fragment {

RecyclerView recyclerView;LinearLayoutManager linearLayoutManager;
String searchpostviewurl="http://10.0.2.2:8080/program/search.php";
String posttt,postid,name,imagepath,datetime,title;RequestQueue requestQueue;
String words;int size;ArrayList<postinfo2> da=new ArrayList<>();
    public searchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

              View v=  inflater.inflate(R.layout.fragment_search, container, false);

               recyclerView=v.findViewById(R.id.rre);
      words= getArguments().getString("words");
      size= getArguments().getInt("size");

        requestQueue= Volley.newRequestQueue(getActivity());
        da.clear();

        search(words,size);


        return v;
    }
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

                        posttt = post.getString("post");
                        name = post.getString("name");
                        imagepath = post.getString("profileimg");
                        title = post.getString("title");
                        datetime=post.getString("date_time");
                        postid=post.getString("postid");
                        String e=post.getString("email");



                        data(posttt,name,imagepath,title,datetime,postid,e);


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

    public void data(String posttt, String name, String imagepath, String title, String datetime, String postid, String e) {
        postinfo2 curren = new postinfo2();

        curren.email = e;
        curren.title = title;
        curren.imagepath = imagepath;
        curren.post = posttt;
        curren.name = name;
        curren.datetime = datetime;
        curren.postid = postid;
        da.add(curren);

        //adapter.filter(da);

        postrecycler2 adapter = new postrecycler2(getActivity(), da);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);


    }
}
