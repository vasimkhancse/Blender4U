package com.example.khanbros.blender4umodel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class commentActivity extends AppCompatActivity {
    TextView email,question;String datetime,d2,name,title,img,postid;
    RequestQueue requestQueue;
    View v;EditText comment;    String posturl="http://10.0.2.2:8080/program/commentpost.php";
    boolean CheckEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


            Toolbar toolbar=(Toolbar) findViewById(R.id.t);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

           getSupportActionBar().setTitle("Comment post");
            email= (TextView) findViewById(R.id.textViewemail);
            email.setText(getIntent().getStringExtra("email"));

       question= (TextView) findViewById(R.id.textView3);
      question.setText(getIntent().getStringExtra("question"));

        postid=getIntent().getStringExtra("postid");

            comment= (EditText) findViewById(R.id.editTexttitle);

            requestQueue= Volley.newRequestQueue(this);

            Date d=new Date();
            SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
            datetime=(s.format(d));

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int i=item.getItemId();
            if(i==android.R.id.home){
         this.finish();
                Intent in=new Intent(this,viewquestion.class);
                in.putExtra("back","0");
                in.putExtra("postid",postid);
                startActivity(in);

            }
            if(i==R.id.post){
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    post();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
            return super.onOptionsItemSelected(item);
        }

    private void post() {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("post successfully")){
                 commentActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(),viewquestion.class);
                    intent.putExtra("back","0");
                    intent.putExtra("postid",postid);


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
                map.put("comment", comment.getText().toString());

                map.put("date_time", datetime);
                map.put("email", email.getText().toString());
                map.put("postid", postid);
                return map;

            }
        };
        requestQueue.add(request);
    }



    public void CheckEditTextIsEmptyOrNot(){

        String tit = comment.getText().toString();





        if(TextUtils.isEmpty(tit)  )
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}

