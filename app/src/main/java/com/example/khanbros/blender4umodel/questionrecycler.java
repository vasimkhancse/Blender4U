package com.example.khanbros.blender4umodel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class questionrecycler extends RecyclerView.Adapter<questionrecycler.myviewholder>{
    List<questionpostinfo> collectdata;ImageLoader imageLoader;int previousposition=0;
    Context context;LayoutInflater inflater;String dtime=" ";String email;
    String countlike;String ss,image;String post,email2,title;Bitmap bitmap;
    RequestQueue requestQueue;SharedPreferences sharedPreferences;
      String likeposturl="http://10.0.2.2:8080/program/likepost.php";
      String  likesurl="http://10.0.2.2:8080/program/likes.php";
    String  likescounturl="http://10.0.2.2:8080/program/countlikes.php";
    String  commentcounturl="http://10.0.2.2:8080/program/countcomment.php";
    public questionrecycler(Context context, List<questionpostinfo> collectdata,String s) {
        this.context=context;this.ss=s;
        this.collectdata=collectdata;
        inflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.questionpost,viewGroup,false);
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

        likeshow(collectdata.get(i).postid,myviewholder.like,myviewholder.unlike);
        myviewholder.name.setText(collectdata.get(i).name);
        myviewholder.title.setText(collectdata.get(i).title);
        myviewholder.postid.setText(collectdata.get(i).postid);

showlikes(myviewholder,i);
        //ithu questionkku
        String s=collectdata.get(i).question;

        if(s.length()>=100 ) {
            s=s.substring(0,100)+" ......";
            myviewholder.question.setText(s);
        }
        else{
            myviewholder.question.setText(s);

        }
          //ithu datetimeku
        String timestamp=getTimestampdifference(collectdata.get(i).datetime);
        if(!timestamp.equals("0")){
            int td= Integer.parseInt(timestamp);
            if(td>=30){
                int t=td/30;
                if(t>=13){
                    String g=collectdata.get(i).datetime;
                    g=g.substring(0,10);
                    myviewholder.datetime.setText(g);

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
//ithu next intentkku

        myviewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,viewquestion.class);
                intent.putExtra("back",ss);
                intent.putExtra("postid",collectdata.get(i).postid);
                context.startActivity(intent);
            }
        });
        sharedPreferences=context.getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
       email=  sharedPreferences.getString(context.getResources().getString(R.string.email),"");
        myviewholder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             like(collectdata.get(i).postid,1,myviewholder.like,myviewholder.unlike,myviewholder,i);

                email2=collectdata.get(i).email;
                post=collectdata.get(i).question;
                image=collectdata.get(i).imagepath;
                title=collectdata.get(i).title;
            }



            });
        myviewholder.unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(collectdata.get(i).postid, 0, myviewholder.like, myviewholder.unlike, myviewholder, i);
            }
        });
        myviewholder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,viewquestion.class);
                intent.putExtra("back",ss);
                intent.putExtra("postid",collectdata.get(i).postid);
                context.startActivity(intent);
            }
        });
        myviewholder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email2=collectdata.get(i).email;
                if(!email.equals(email2)){
                Intent in = new Intent(context, friendprofileActivity.class);
                in.putExtra("email", collectdata.get(i).email);

                context.startActivity(in);}
            }
        });
        myviewholder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=collectdata.get(i).question;

                if(s.length()>=100 ) {

                }
                else {
                    String w=  myviewholder.question.getText().toString();
                    if(w.contains("http://")||w.contains("https://")) {
                        // Toast.makeText(getApplicationContext(),w.indexOf("http") + " semma", Toast.LENGTH_LONG).show();
                        int a = w.indexOf("http");


                        int k=a+1;String f="";
                        myviewholder.question.setLinkTextColor(Color.BLUE);
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

            }
        });
        }

    private void showlikes(myviewholder myviewholder, int i) {
        countlike=collectdata.get(i).postid;
        likecount(countlike,myviewholder.textlikes);
        commentcount(collectdata.get(i).postid,myviewholder.textcomments);

    }

    private void likecount(final  String postid, final TextView textlikes) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, likescounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            //    Toast.makeText(context,response,Toast.LENGTH_SHORT).show();

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

                map.put("postid", postid);

                return map;

            }
        };
        requestQueue.add(request);
    }
    private void commentcount(final  String postid,final TextView textView) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, commentcounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            //    Toast.makeText(context,response,Toast.LENGTH_SHORT).show();


                int a= Integer.parseInt(response);

                int b=a;
                if(a==0){
                    textView.setText(" ");


                }
                else{
                    if(a<=1000){

                        textView.setText(a+"comments");
                    }
                    else {
                        a=a/1000;
                        String y=String.valueOf(a);
                        if(a<=999){
                            if(a<=99){
                                if(!y.contains(".")) {
                                    textView.setText(y + "k comments");
                                }
                                else {

                                    y=y.substring(0,3);
                                    textView.setText(y+"k comments");}}
                            else {
                                if(!y.contains(".")) {
                                    textView.setText(y + "k comments");
                                }
                                else {
                                    y=y.substring(0,4);
                                    textView.setText(y+"k comments");}}}
                        else {
                            a=b/1000000;
                            String e=String.valueOf(a);
                            if(!e.contains(".")) {
                                textView.setText(e + "M comments");
                            }
                            else {
                                e=e.substring(0,3);

                                textView.setText(e+"M comments");
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

                map.put("postid", postid);

                return map;

            }
        };
        requestQueue.add(request);
    }
    private void likeshow(final String postid, final CircleImageView like, final CircleImageView unlike) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, likesurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            //    Toast.makeText(context,response,Toast.LENGTH_SHORT).show();


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

                map.put("postid", postid);
                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }


    private void like(final String postid, final int no, final CircleImageView like, final CircleImageView unlike, final myviewholder myviewholder, final int i) {
        requestQueue= Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, likeposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                showlikes(myviewholder,i);

                if(response.equalsIgnoreCase("liked")){
                like.setVisibility(View.INVISIBLE);
                unlike.setVisibility(View.VISIBLE);

                Animation.like(context,unlike);
                    final String img = "http://10.0.2.2:8080/program/" + image;


                    imageLoader.displayImage(img, myviewholder.imageView, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            bitmap = loadedImage;
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });
                    Handler h=new Handler();
                    Runnable r=new Runnable() {
                        @Override
                        public void run() {
                            if(email.equals(email2)){

                            }else {
                               // notification(postid,myviewholder,i);
                            }


                        }
                    };
                    h.postDelayed(r,1000);
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
                map.put("postid", postid);
                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }
    private void notification(String postid, final questionrecycler.myviewholder myviewholder, int i) {
   int id = 1;
            Intent in = new Intent(context, viewquestion.class);
            in.putExtra("postid", postid);
            in.putExtra("back","0");
            PendingIntent pendingIntent3 = PendingIntent.getActivity(context, id, in, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification n3 = new NotificationCompat.Builder(context)
                    .setContentTitle(" like your post")
                    .setTicker("BLENDER 4U").setColor(6767767)
                    .setLargeIcon(bitmap).setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("your post liked in " + myviewholder.textlikes.getText().toString())
                            .setSummaryText(title).bigText(post)
                    )
                    .setSmallIcon(R.drawable.blender)
                    .setContentText("your post liked in " + myviewholder.textlikes.getText().toString())
                    .setAutoCancel(true).setContentIntent(pendingIntent3).build();

            NotificationManager nm3 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm3.notify(id, n3);
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
public void setfilter(List<questionpostinfo> data){
        collectdata=new ArrayList<>();
        collectdata.addAll(data);
        notifyDataSetChanged();
}

    class myviewholder extends RecyclerView.ViewHolder{
        TextView textcomments,title,question,name,datetime,postid,textlikes;CircleImageView imageView,like,unlike,comment;RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textViewname);
            title=itemView.findViewById(R.id.textViewtitle);
          question=itemView.findViewById(R.id.textViewquestion);
            imageView=itemView.findViewById(R.id.img);
            datetime=itemView.findViewById(R.id.textViewdate);
            relativeLayout=itemView.findViewById(R.id.r);
            postid=itemView.findViewById(R.id.textViewpostid);
            like=itemView.findViewById(R.id.img2);
            comment=itemView.findViewById(R.id.img3);
            unlike=itemView.findViewById(R.id.img4);
            textlikes=itemView.findViewById(R.id.textViewlikes);
            textcomments=itemView.findViewById(R.id.textcomments);
        }
    }
}
