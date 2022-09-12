package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class commentrecycler extends RecyclerView.Adapter<commentrecycler.myviewholder>{
    List<commentpostinfo> collectdata;ImageLoader imageLoader;int previousposition;
    Context context;LayoutInflater inflater;String dtime=" ";String email;
    String countlike;
    String commentposturl="http://10.0.2.2:8080/program/commentpost2.php";
    String  likesurl="http://10.0.2.2:8080/program/commentlikes.php";
    String  likescounturl="http://10.0.2.2:8080/program/countcommentlikes.php";
    RequestQueue requestQueue;SharedPreferences sharedPreferences;ArrayList<String> likelist;

    public commentrecycler(Context context, List<commentpostinfo> collectdata) {
        this.context=context;
        this.collectdata=collectdata;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.commentpost,viewGroup,false);
        myviewholder myviewholder=new myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder myviewholder, final int i) {

        if(i>previousposition){
            Animation.animate(myviewholder,true);
        }
        else {
            Animation.animate(myviewholder,false);

        }
        previousposition=i;


        likelist=new ArrayList<>();
        likeshow(collectdata.get(i).commentid,myviewholder.like,myviewholder.unlike);
        myviewholder.name.setText(collectdata.get(i).name);
        myviewholder.commentt.setText(collectdata.get(i).comment);
       myviewholder.commentid.setText(collectdata.get(i).commentid);

showlikes(myviewholder, i);

          //ithu datetimeku
        String timestamp=getTimestampdifference(collectdata.get(i).datetime);
        if(!timestamp.equals("0")){
            int td= Integer.parseInt(timestamp);
            if(td>=30){
                int t=td/30;
                if(t>=13){
                    myviewholder.datetime.setText(collectdata.get(i).datetime);

                }
                else {
                    int n=td/30;
                    String e= String.valueOf(n);
                    String o=e.substring(0,1);

                    if(n>=9){
                        e=e.substring(0,2);
                        myviewholder.datetime.setText(e + " month ago");

                    }
                    else {
                        myviewholder.datetime.setText(o + " month ago");

                    }

                }

            }
            else {
                myviewholder.datetime.setText(timestamp + " days ago");
            }
        }
        else {
            if(dtime.equals("0")){
                myviewholder.datetime.setText("Just Now");

            }
            else {
                if(dtime.contains("-")){
                    myviewholder.datetime.setText("TODAY");}
                else {
                    myviewholder.datetime.setText(dtime + " hours ago");
                }
            }
        }

       //ithu imagekku
        final String photo="http://10.0.2.2:8080/program/"+collectdata.get(i).imagepath;

        imageLoader= ImageLoader.getInstance();
        imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
        DisplayImageOptions options= new DisplayImageOptions.Builder()
            //    .cacheInMemory(true).cacheOnDisk(true)
                .showImageOnLoading(R.drawable.blender).build();


        if(collectdata.get(i).imagepath.startsWith("http")||collectdata.get(i).imagepath.startsWith("con")){
            imageLoader.displayImage(collectdata.get(i).imagepath,myviewholder.imageView,options);
        }
        else {
            imageLoader.displayImage(photo,myviewholder.imageView,options);
        }

        myviewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sharedPreferences=context.getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(context.getResources().getString(R.string.email),"");

        myviewholder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                like(collectdata.get(i).commentid,1,myviewholder.like,myviewholder.unlike,myviewholder,i);


            }



        });
        myviewholder.unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(collectdata.get(i).commentid, 0, myviewholder.like, myviewholder.unlike, myviewholder, i);
            }
        });


        myviewholder.commentt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w=  myviewholder.commentt.getText().toString();
                if(w.contains("http://")||w.contains("https://")) {
                    // Toast.makeText(getApplicationContext(),w.indexOf("http") + " semma", Toast.LENGTH_LONG).show();
                    int a = w.indexOf("http");


                    int k=a+1;String f="";
                   myviewholder.commentt.setLinkTextColor(Color.BLUE);
                    lable:while (w.charAt(k)!=' '){
                        f=w.substring(a,k+1);
                        k++;
                        continue lable;
                    }
                    Intent i=new Intent(context,webActivity.class);
                    i.putExtra("url",f);
                    context.startActivity(i);






                }
            }
        });


    }

    private void showlikes(commentrecycler.myviewholder myviewholder, int i) {
        countlike=collectdata.get(i).commentid;
        likecount(countlike,myviewholder.textlikes);
    }

    private void likecount(final  String commentid, final TextView textlikes) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, likescounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    textlikes.setText(" ");


                }
                else{
                    if(a<=1000){

                        textlikes.setText(a+"likes");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    textlikes.setText(y + "k likes");
                                }
                                else {

                                    y=y.substring(0,3);
                                    textlikes.setText(y+"k likes");}}
                            else {
                                if(!y.contains(".")) {
                                    textlikes.setText(y + "k likes");
                                }
                                else {
                                    y=y.substring(0,4);
                                    textlikes.setText(y+"k likes");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                textlikes.setText(e + "M likes");
                            }
                            else {
                                e=e.substring(0,3);

                                textlikes.setText(e+"M likes");
                            }}

                    }

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

                map.put("commentid", commentid);

                return map;

            }
        };
        requestQueue.add(request);
    }




    private void like(final String commentid, final int no, final CircleImageView like, final CircleImageView unlike, final commentrecycler.myviewholder myviewholder, final int i) {
        requestQueue= Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, commentposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                showlikes(myviewholder,i);

                if(response.equalsIgnoreCase("liked")){
                    like.setVisibility(View.INVISIBLE);
                    unlike.setVisibility(View.VISIBLE);
                }
                else if(response.equalsIgnoreCase("unliked")){
                    unlike.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
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
                map.put("no", String.valueOf(no));
                map.put("commentid", commentid);
                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }

    private void likeshow(final String commentid, final CircleImageView like, final CircleImageView unlike) {
        requestQueue = Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, likesurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                if (response.equalsIgnoreCase("liked")) {

                    like.setVisibility(View.INVISIBLE);
                    unlike.setVisibility(View.VISIBLE);
                } else if (response.equalsIgnoreCase("unliked")) {
                    unlike.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
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

                map.put("commentid", commentid);
                map.put("email", email);
                return map;

            }
        };
        requestQueue.add(request);
    }


    private String getTimestampdifference(String sdate) {
        Date date=new Date();
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        Date todaytime=calendar.getTime();
        s.format(todaytime);
        String diff=" ";
        Date timestramp;
        try {
            timestramp=s.parse(sdate);
            diff=String.valueOf(Math.round((todaytime.getTime()-timestramp.getTime())/1000/60/60/24));
            if(diff.equals("0")){
                dtime=String.valueOf(todaytime.getHours()-timestramp.getHours());
                return diff;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

return diff;
    }

    @Override
    public int getItemCount() {
        return collectdata.size();
    }


    class myviewholder extends RecyclerView.ViewHolder{
        TextView commentt,name,datetime,commentid,textlikes;CircleImageView imageView,like,unlike,comment;RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textViewname);
        commentt=itemView.findViewById(R.id.textViewcomment);
            imageView=itemView.findViewById(R.id.img);
            datetime=itemView.findViewById(R.id.textViewdate);
            relativeLayout=itemView.findViewById(R.id.r);
           commentid=itemView.findViewById(R.id.textViewcommentid);
            like=itemView.findViewById(R.id.img2);
            comment=itemView.findViewById(R.id.img3);
            unlike=itemView.findViewById(R.id.img4);
            textlikes=itemView.findViewById(R.id.textViewlikes);
        }
    }
}
