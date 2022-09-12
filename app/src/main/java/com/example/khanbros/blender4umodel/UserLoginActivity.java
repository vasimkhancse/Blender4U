package com.example.khanbros.blender4umodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserLoginActivity extends AppCompatActivity {

    TextInputEditText Email, Password;RequestQueue requestQueue;
    Button LogIn ,register;
    String PasswordHolder, EmailHolder;static String myprefer="MyPrefer";static String email="emailKey";
    SharedPreferences sharedPreferences;
    String finalResult ;
    String url="http://10.0.2.2:8080/program/userlogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Email =  findViewById(R.id.email);
        Password =  findViewById(R.id.password);
        LogIn = (Button) findViewById(R.id.Login);

        register = (Button) findViewById(R.id.Submit);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // CheckEditTextIsEmptyOrNot();
                EmailHolder = Email.getText().toString();
                PasswordHolder = Password.getText().toString();

                if(TextUtils.isEmpty(EmailHolder) )
                {
                    Email.setError("please fill form detail");
                }
               else if(TextUtils.isEmpty(PasswordHolder) )
                {
                    Password.setError("please fill form detail");
                }
                else {

                    UserLoginFunction(EmailHolder, PasswordHolder);
                    progressDialog = ProgressDialog.show(UserLoginActivity.this,"Loading Data",null,true,true);

                }



            }
        });


    }

    private void UserLoginFunction(final String emailHolder, final String passwordHolder) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("Data Matched")){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(getResources().getString(R.string.email),EmailHolder);
                    editor.commit();

                    finish();

                    Intent intent = new Intent(UserLoginActivity.this, MainRnewsfeedActivity.class);


                    startActivity(intent);}


                else
                {
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("password", passwordHolder);
                map.put("email", emailHolder);
                return map;

            }
        };
        requestQueue.add(request);
    }

    public void CheckEditTextIsEmptyOrNot(){


    }
    public void register(View view) {
        Intent intent = new Intent(UserLoginActivity.this,Register_Activity.class);



        startActivity(intent);
    }



    @Override
    protected void onResume() {
        sharedPreferences=getSharedPreferences(myprefer, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(getResources().getString(R.string.email))){
            Intent intent = new Intent(UserLoginActivity.this, MainRnewsfeedActivity.class);
            startActivity(intent);

        }
        super.onResume();
    }}