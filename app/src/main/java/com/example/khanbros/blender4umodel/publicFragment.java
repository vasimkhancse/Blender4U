package com.example.khanbros.blender4umodel;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
public class publicFragment extends Fragment {
    TextView show;
    String name,imagepath,title,email;SharedPreferences sharedPreferences;
    RequestQueue requestQueue;friendcaserecycler friendcaserecycler;RecyclerView rt;
    ArrayList<friendpostinfo> d=new ArrayList<>();ImageView filter;
    String usersviewurl="http://10.0.2.2:8080/program/userspost.php";
    String usersviewurl3="http://10.0.2.2:8080/program/userspost(copy).php";
    String usersviewurl2="http://10.0.2.2:8080/program/userssort3.php";
    String text,words;

    public publicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_public, container, false);
        sharedPreferences=getActivity().getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(getResources().getString(R.string.email),"");
        rt=v.findViewById(R.id.rt);
        show=v.findViewById(R.id.textView);
        if(getArguments().getString("no").equals("0")){
            email=getArguments().getString("email");
        }
        usersshow();

        filter=v.findViewById(R.id.imageView7);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
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
                        show.setText(text);
                        spinner();
                    }
                });

                Spinner spinner=v.findViewById(R.id.spinner);
                String[] a={"All","High followers","public","All Users"};
                ArrayAdapter<String> s=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,a);
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


        });




        return v;
    }

    public  void spinner(){
        if(text.equals("public")){
            d.clear();

           usersshow();
        }
        if(text.equals("High followers")){
            d.clear();

            userssort();
        }
        if(text.equals("All Users")){
            d.clear();

            usersshow2();
        }
    }

    public void usersshow2() {
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

    public void usersshow() {
        requestQueue= Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST,usersviewurl3, new Response.Listener<String>() {
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

        friendcaserecycler=new friendcaserecycler(getActivity(),d,3);
        rt.setAdapter(friendcaserecycler);
        rt.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rt.setHasFixedSize(false);rt.setNestedScrollingEnabled(false);


    }
    public void userssort() {
        requestQueue= Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST,usersviewurl2, new Response.Listener<String>() {
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

                        getdata2(name,imagepath,title);
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

    private void getdata2( String name, String imagepath, String email) {
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