package com.example.khanbros.blender4umodel;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class questionFragment extends Fragment {


   TextView email,question;String datetime,no,postid;
    AppCompatActivity activity;RequestQueue requestQueue;
    View v;EditText title;    String posturl="http://10.0.2.2:8080/program/questionpost.php";
    boolean CheckEditText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_question, container, false);
     activity = (AppCompatActivity) getActivity();
        Toolbar toolbar=(Toolbar) v.findViewById(R.id.t);
       activity.setSupportActionBar(toolbar);

       activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

      activity.getSupportActionBar().setTitle(" Question post");
      email=v.findViewById(R.id.textViewemail);
      email.setText(getArguments().getString("email"));
//option menukku use
setHasOptionsMenu(true);

title=v.findViewById(R.id.editTexttitle);
question=v.findViewById(R.id.editTextquestion);
        requestQueue= Volley.newRequestQueue(getActivity());

        Date d=new Date();
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
       datetime=(s.format(d));

      no=  getArguments().getString("no");
       if(no.equals("1")){
          title.setText(getArguments().getString("title"));
          question.setText(getArguments().getString("question"));
           postid=getArguments().getString("postid");
       }

        return  v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        if(i==android.R.id.home){
            if(no.equals("1")){
                getActivity().finish();
                Intent intent = new Intent(getActivity(),viewquestion.class);
                intent.putExtra("postid",postid);
                intent.putExtra("back","0");
                startActivity(intent);
            }else {
            getActivity().finish();
            Intent in=new Intent(getActivity(),QuestionActivity.class);
            startActivity(in);}

        }
        if(i==R.id.post){
            CheckEditTextIsEmptyOrNot();
            if(CheckEditText){
                if(no.equals("1")){

                    postupdate();

                }
                else {
                post();}

            }
            else {
                Toast.makeText(getActivity(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }
    private void postupdate() {
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("success")){
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(),viewquestion.class);
                    intent.putExtra("postid",postid);
                    intent.putExtra("back","0");
                    startActivity(intent);
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
                map.put("title", title.getText().toString());
                map.put("question", question.getText().toString());
                map.put("postid",postid);
                map.put("no",no);
                map.put("email", email.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);
    }
    private void post() {
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("post successfully")){
                   getActivity().finish();
                    Intent intent = new Intent(getActivity(),QuestionActivity.class);

                    startActivity(intent);
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
                map.put("title", title.getText().toString());
                map.put("question", question.getText().toString());
                map.put("date_time", datetime);
                map.put("email", email.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);
    }



    public void CheckEditTextIsEmptyOrNot(){

        String tit = title.getText().toString();

       String ques= question.getText().toString();



        if(TextUtils.isEmpty(tit) || TextUtils.isEmpty(ques) )
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
}
