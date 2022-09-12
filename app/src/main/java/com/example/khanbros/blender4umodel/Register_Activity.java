package com.example.khanbros.blender4umodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class Register_Activity extends AppCompatActivity {

    Button register;Context context=Register_Activity.this;
    TextInputEditText Name, Email, Password ;
    String Name_Holder, EmailHolder, PasswordHolder;
    String finalResult ;RequestQueue requestQueue;
    String url="http://10.0.2.2:8080/program/userregister.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Name = findViewById(R.id.editTextF_Name);

        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);

        register = (Button)findViewById(R.id.Submit);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // CheckEditTextIsEmptyOrNot();

                Name_Holder = Name.getText().toString();

                EmailHolder = Email.getText().toString();
                PasswordHolder = Password.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(TextUtils.isEmpty(Name_Holder) )
                {
                    Name.setError( "Please fill  form fields");


                }
                if(TextUtils.isEmpty(EmailHolder) )
                {
                    Email.setError( "Please fill  form fields");


                }
                if(TextUtils.isEmpty(PasswordHolder) )
                {
                    Password.setError( "Please fill  form fields");


                }
                else  if(!EmailHolder.matches(emailPattern)){

                        Email.setError("no email");

                }
                else{

                    UserRegisterFunction(Name_Holder, EmailHolder, PasswordHolder);
                    progressDialog = ProgressDialog.show(context,"Loading Data",null,true,true);


                }



            }
        });



    }

    private void UserRegisterFunction( final String name_holder, final String emailHolder, final String passwordHolder) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                if(response.equalsIgnoreCase("Email Already Exist")){
                    Toast.makeText(getApplicationContext(),response+  " type change name",Toast.LENGTH_SHORT).show();

                }
                else {
                    finish();
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, UserLoginActivity.class);


                    startActivity(intent);
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name_holder);
                map.put("password", passwordHolder);
                map.put("email", emailHolder);
                return map;

            }
        };
        requestQueue.add(request);
    }

    public void CheckEditTextIsEmptyOrNot(){



    }







}

