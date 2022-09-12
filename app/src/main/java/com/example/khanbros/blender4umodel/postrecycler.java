package com.example.khanbros.blender4umodel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
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
import ru.whalemare.sheetmenu.SheetMenu;

class postrecycler extends RecyclerView.Adapter<postrecycler.myviewholder>{
    List<postinfo> collectdata;ImageLoader imageLoader;
    Context context;LayoutInflater inflater;String dtime=" ";String email,email2,post,title,image;
    String countlike;  Bitmap bitmap =null;RecyclerView recyclerView;
    RequestQueue requestQueue;SharedPreferences sharedPreferences;int y;int previousposition=0;
    String likeposturl="http://10.0.2.2:8080/program/likepost.php";
    String  likesurl="http://10.0.2.2:8080/program/likes.php";
    String  likescounturl="http://10.0.2.2:8080/program/countlikes.php";
    String  commentcounturl="http://10.0.2.2:8080/program/countcomment.php";
    public postrecycler(Context context, List<postinfo> collectdata) {

        this.context=context;
        this.collectdata=collectdata;

        inflater=LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.post,viewGroup,false);
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
       String a=collectdata.get(i).title;
       if(a.length()>=200){
           myviewholder.title.setTextSize(16);
           myviewholder.title.setText(a);
       }
       else if(a.length()<=100){
           myviewholder.title.setTextSize(23);
           myviewholder.title.setText(a);
       }else {
           myviewholder.title.setTextSize(18);
           myviewholder.title.setText(a);
       }
        myviewholder.postid.setText(collectdata.get(i).postid);

showlikes(myviewholder,i);
        //ithu questionkku
       final String s=collectdata.get(i).post;
        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){
            myviewholder.question.setVisibility(View.INVISIBLE);
            myviewholder.videoView.setVisibility(View.INVISIBLE);
            myviewholder.imageView2.setVisibility(View.VISIBLE);
            myviewholder.youtube.setVisibility(View.INVISIBLE);
            myviewholder.youTubeThumbnailView.setVisibility(View.INVISIBLE);
            myviewholder.progressBar.setVisibility(View.GONE);
            myviewholder.play.setVisibility(View.GONE);
            final String img="http://10.0.2.2:8080/program/"+s;

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.blender).build();

            if(s.startsWith("http")||s.startsWith("con")){
                imageLoader.displayImage(s,myviewholder.imageView2,options);
            }
            else {
                imageLoader.displayImage(img, myviewholder.imageView2,options);
            }
        }
        else if(s.endsWith("mp4")||s.endsWith("3gp")){
            myviewholder.question.setVisibility(View.INVISIBLE);
            myviewholder.videoView.setVisibility(View.VISIBLE);
            myviewholder.imageView2.setVisibility(View.INVISIBLE);
            myviewholder.youTubeThumbnailView.setVisibility(View.INVISIBLE);
            myviewholder.youtube.setVisibility(View.INVISIBLE);
            myviewholder.play.setVisibility(View.VISIBLE);

            final String vide="http://10.0.2.2:8080/program/"+s;

          // MediaController mc=new MediaController(context);
          // mc.setAnchorView(myviewholder.videoView);
         //   mc.setMediaPlayer(myviewholder.videoView);
            Uri video=Uri.parse(vide);
         // myviewholder.videoView.setMediaController(mc);
            myviewholder.videoView.setVideoURI(video);
            myviewholder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    myviewholder.progressBar.setVisibility(View.GONE);
                     mp.setVolume(0,0);
                     myviewholder.videoView.start();


                }
            });


        }
        else if(s.startsWith("https://youtu.be")){
            myviewholder.play.setVisibility(View.GONE);
            myviewholder.youtube.setVisibility(View.VISIBLE);
            myviewholder.progressBar.setVisibility(View.VISIBLE);
            myviewholder.question.setVisibility(View.INVISIBLE);
            myviewholder.videoView.setVisibility(View.INVISIBLE);
            myviewholder.imageView2.setVisibility(View.VISIBLE);
            myviewholder.youTubeThumbnailView.setVisibility(View.GONE);
            String img_url="http://img.youtube.com/vi/"+s.substring(17,28)+"/0.jpg";

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                   .build();

                imageLoader.displayImage(img_url, myviewholder.imageView2, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        myviewholder.youtube.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        myviewholder.youtube.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        myviewholder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        myviewholder.youtube.setVisibility(View.VISIBLE);
                    }
                });


           /*     myviewholder.youTubeThumbnailView.initialize("AIzaSyABQYpFQwEc5RAr924e37Wvg1XEKhI_KJI", new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        youTubeThumbnailLoader.setVideo(s.substring(17,28));

                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                myviewholder.progressBar.setVisibility(View.GONE);
                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {


                    }
                });


                        */





        }

        else {
            myviewholder.play.setVisibility(View.GONE);
            myviewholder.youtube.setVisibility(View.INVISIBLE);
             myviewholder.progressBar.setVisibility(View.GONE);
            myviewholder.question.setVisibility(View.VISIBLE);
            myviewholder.videoView.setVisibility(View.INVISIBLE);
            myviewholder.imageView2.setVisibility(View.INVISIBLE);
            myviewholder.youTubeThumbnailView.setVisibility(View.INVISIBLE);




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


        imageLoader.displayImage(photo,myviewholder.imageView,options);
//ithu next intentkku

        myviewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myviewholder.question.getVisibility()==View.VISIBLE){
                    Intent in=new Intent(context,viewpost.class);
                    in.putExtra("postid",collectdata.get(i).postid);
                    in.putExtra("back"," ");
                    context.startActivity(in);
                }
                else {
                    Intent in=new Intent(context,ViewActivity.class);
                    in.putExtra("post",collectdata.get(i).post);
                    context.startActivity(in);
                }
            }
        });
        myviewholder.rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent in=new Intent(context,viewpost.class);
                    in.putExtra("postid",collectdata.get(i).postid);
                in.putExtra("back"," ");
                    context.startActivity(in);

            }
        });
        myviewholder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(context,viewpost.class);
                in.putExtra("postid",collectdata.get(i).postid);
                in.putExtra("back","0");
                context.startActivity(in);

            }
        });
        sharedPreferences=context.getSharedPreferences(UserLoginActivity.myprefer,Context.MODE_PRIVATE);
       email=  sharedPreferences.getString(context.getResources().getString(R.string.email),"");
        myviewholder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(collectdata.get(i).postid,1,myviewholder.like,myviewholder.unlike,myviewholder,i);
email2=collectdata.get(i).email;
post=collectdata.get(i).post;
title=collectdata.get(i).title;image=collectdata.get(i).imagepath;
            }



        });
        myviewholder.unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(collectdata.get(i).postid, 0, myviewholder.like, myviewholder.unlike, myviewholder, i);
            }
        });
        myviewholder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email2=collectdata.get(i).email;
                if(!email.equals(email2)){
                    Intent in = new Intent(context, friendprofileActivity.class);
                    in.putExtra("email", collectdata.get(i).email);
                    in.putExtra("no", " ");
                    context.startActivity(in);}
            }
        });

        myviewholder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });



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
    private void showlikes(postrecycler.myviewholder myviewholder, int i) {
        countlike=collectdata.get(i).postid;
        likecount(countlike,myviewholder.textlikes);
       commentcount(collectdata.get(i).postid,myviewholder.textcomments);

    }
    private void commentcount(final  String postid,final TextView textView) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, commentcounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
    private void likecount(final  String postid, final TextView textlikes) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, likescounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                int a = Integer.parseInt(response);

                int b = a;
                if (a == 0) {
                    textlikes.setText(" ");


                } else {
                    if (a <= 1000) {

                        textlikes.setText(a + "likes");
                    } else {
                        a = a / 1000;
                        String y = String.valueOf(a);
                        if (a <= 999) {
                            if (a <= 99) {
                                if (!y.contains(".")) {
                                    textlikes.setText(y + "k likes");
                                } else {

                                    y = y.substring(0, 3);
                                    textlikes.setText(y + "k likes");
                                }
                            } else {
                                if (!y.contains(".")) {
                                    textlikes.setText(y + "k likes");
                                } else {
                                    y = y.substring(0, 4);
                                    textlikes.setText(y + "k likes");
                                }
                            }
                        } else {
                            a = b / 1000000;
                            String e = String.valueOf(a);
                            if (!e.contains(".")) {
                                textlikes.setText(e + "M likes");
                            } else {
                                e = e.substring(0, 3);

                                textlikes.setText(e + "M likes");
                            }
                        }

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

             //   Toast.makeText(context,response,Toast.LENGTH_SHORT).show();


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

    private void like(final String postid, final int no, final CircleImageView like, final CircleImageView unlike, final postrecycler.myviewholder myviewholder, final int i) {
        requestQueue= Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, likeposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                showlikes(myviewholder,i);

                if(response.equalsIgnoreCase("liked")){
                    like.setVisibility(View.INVISIBLE);
                    unlike.setVisibility(View.VISIBLE);


                    Animation.like(context,unlike);

                    if(post.endsWith("jpg")||post.endsWith("jpeg")||post.endsWith("png")) {
                        final String img = "http://10.0.2.2:8080/program/" + post;


                        imageLoader.displayImage(img, myviewholder.imageView2, new ImageLoadingListener() {
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
                        });}
                        else if(post.endsWith("3gp")||post.endsWith("mp4")){
                        final String img = "http://10.0.2.2:8080/program/" + post;
                        Glide.with(context)
                                .asBitmap().load(img)
                                .listener(new RequestListener<Bitmap>() {
                                              @Override
                                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                                  return false;
                                              }

                                              @Override
                                              public boolean onResourceReady(Bitmap map, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                                  bitmap=map;
                                                  return false;
                                              }
                                          }
                                ).submit();
                    }
                        else  {
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
                        });}
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

    private void notification(String postid, final myviewholder myviewholder, int i) {
        if(post.endsWith("jpg")||post.endsWith("jpeg")||post.endsWith("png")||post.endsWith("mp4")||post.endsWith("3gp")) {



            int id = 0;
            Intent in = new Intent(context, viewpost.class);
            in.putExtra("postid", postid);
            in.putExtra("back","0");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, in, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification n = new NotificationCompat.Builder(context)
                    .setContentTitle(" like your post")
                    .setTicker("BLENDER 4U").setColor(6767767)
                    .setLargeIcon(bitmap).setOnlyAlertOnce(true).setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("your post liked in " + myviewholder.textlikes.getText().toString())

                            .setSummaryText(title).bigLargeIcon(null)
                    )
                    .setSmallIcon(R.drawable.ic_profile)
                    .setContentText("your post liked in " + myviewholder.textlikes.getText().toString())
                    .setAutoCancel(true).setContentIntent(pendingIntent).build();

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(id, n);
        }

       else  {
            if(post.startsWith("http")){
                post=title;
            }

            int id = 1;
            Intent in = new Intent(context, viewpost.class);
            in.putExtra("postid", postid);
            in.putExtra("back","0");
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, id, in, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification n2 = new NotificationCompat.Builder(context)
                    .setContentTitle(" like your post")
                    .setTicker("BLENDER 4U").setColor(6767767)
                    .setLargeIcon(bitmap).setOnlyAlertOnce(true).setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                   .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("your post liked in " + myviewholder.textlikes.getText().toString())
                   .setSummaryText(title).bigText(post)
                   )
                    .setSmallIcon(R.drawable.blender)
                    .setContentText("your post liked in " + myviewholder.textlikes.getText().toString())
                    .setAutoCancel(true).setContentIntent(pendingIntent2).build();

            NotificationManager nm2 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm2.notify(id, n2);
        }


    }

    @Override
    public int getItemCount() {
        return collectdata.size();
    }
public void setfilter(List<postinfo> data){
        collectdata=new ArrayList<>();

        collectdata.addAll(data);
        notifyDataSetChanged();
}
    public void filter(List<postinfo> data){
        collectdata=new ArrayList<>();

        collectdata.addAll(data);
        notifyDataSetChanged();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        VideoView videoView;YouTubeThumbnailView youTubeThumbnailView;ProgressBar progressBar;
        TextView textcomments,title,question,name,datetime,postid,textlikes;ImageView play,imageView2,youtube;CircleImageView imageView,like,unlike,comment;RelativeLayout relativeLayout;CardView rr;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textViewname);
            title=itemView.findViewById(R.id.textViewtitle);
          question=itemView.findViewById(R.id.textViewquestion);
            imageView=itemView.findViewById(R.id.img);
            imageView2=itemView.findViewById(R.id.imageView2);
            play=itemView.findViewById(R.id.imageViewplay);
            youtube=itemView.findViewById(R.id.imageViewyb);
            videoView=itemView.findViewById(R.id.videoView2);
youTubeThumbnailView=itemView.findViewById(R.id.y);
            datetime=itemView.findViewById(R.id.textViewdate);
            relativeLayout=itemView.findViewById(R.id.r);
            rr=itemView.findViewById(R.id.rr);
            postid=itemView.findViewById(R.id.textViewpostid);
            like=itemView.findViewById(R.id.img2);
            comment=itemView.findViewById(R.id.img3);
            unlike=itemView.findViewById(R.id.img4);
            textlikes=itemView.findViewById(R.id.textViewlikes);
            textcomments=itemView.findViewById(R.id.textcomments);
            progressBar=itemView.findViewById(R.id.progressBar2);

        }
    }

}
