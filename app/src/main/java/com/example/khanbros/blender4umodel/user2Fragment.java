package com.example.khanbros.blender4umodel;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class user2Fragment extends Fragment {

    String name,imagepath,title,email;SharedPreferences sharedPreferences;
RequestQueue requestQueue;friendcaserecycler friendcaserecycler;RecyclerView rt;
    ArrayList<friendpostinfo> d=new ArrayList<>();   String usersviewurl="http://10.0.2.2:8080/program/userspost3.php";
    public user2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_user2, container, false);
        sharedPreferences=getActivity().getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        rt=v.findViewById(R.id.rt);
        if(getArguments().getString("no").equals("0")){
            email=getArguments().getString("email");
        }
        usersshow();
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

        friendcaserecycler=new friendcaserecycler(getActivity(),d,2);
        rt.setAdapter(friendcaserecycler);
        rt.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rt.setHasFixedSize(false);rt.setNestedScrollingEnabled(false);


    }
}
