package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
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

class showcaserecycler extends RecyclerView.Adapter<showcaserecycler.myviewholder>{
    List<showcase> collectdata;ImageLoader imageLoader;
    Context context;LayoutInflater inflater;String dtime=" ";String email;
    String countlike;String s;int previousposition=0;
    RequestQueue requestQueue;SharedPreferences sharedPreferences;
    public showcaserecycler(Context context, List<showcase> collectdata,String s) {
        this.context=context;
        this.collectdata=collectdata;this.s=s;

        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.showcaes,viewGroup,false);
        myviewholder myviewholder=new myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder myviewholder, final int i) {

        myviewholder.title.setText(collectdata.get(i).title);
        myviewholder.postid.setText(collectdata.get(i).postid);
       final String s=collectdata.get(i).post;
        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){
            myviewholder.postid.setVisibility(View.INVISIBLE);
            myviewholder.videoView.setVisibility(View.INVISIBLE);
            myviewholder.imageView2.setVisibility(View.VISIBLE);
            myviewholder.youtube.setVisibility(View.INVISIBLE);
            myviewholder.youTubeThumbnailView.setVisibility(View.INVISIBLE);
            myviewholder.progressBar.setVisibility(View.VISIBLE);
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
                imageLoader.displayImage(img,myviewholder.imageView2,options);
                myviewholder.progressBar.setVisibility(View.GONE);

            }
        }
        else if(s.endsWith("mp4")||s.endsWith("3gp")){
            myviewholder.progressBar.setVisibility(View.VISIBLE);
            myviewholder.postid.setVisibility(View.INVISIBLE);
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
          myviewholder. videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                   myviewholder. progressBar.setVisibility(View.GONE);
                    mp.setVolume(0,0);
                    myviewholder.videoView.start();
                }
            });

        }
        else if(s.startsWith("https://youtu.be")){
            myviewholder.progressBar.setVisibility(View.VISIBLE);
            myviewholder.youtube.setVisibility(View.VISIBLE);
            myviewholder.postid.setVisibility(View.INVISIBLE);
            myviewholder.videoView.setVisibility(View.INVISIBLE);
            myviewholder.imageView2.setVisibility(View.VISIBLE);   myviewholder.play.setVisibility(View.GONE);
            myviewholder.youTubeThumbnailView.setVisibility(View.GONE);
            String img_url="http://img.youtube.com/vi/"+s.substring(17,28)+"/0.jpg";

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.blender)
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
          /*  myviewholder.youTubeThumbnailView.initialize("AIzaSyABQYpFQwEc5RAr924e37Wvg1XEKhI_KJI", new YouTubeThumbnailView.OnInitializedListener() {
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
                            //  youTubeThumbnailView.setImageResource(R.drawable.blender);
                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                   // youTubeThumbnailView.setImageResource(R.drawable.blender);

                }
            });*/


        }
        else {
        }
        myviewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(context,viewpost.class);
                in.putExtra("postid",collectdata.get(i).postid);
                in.putExtra("back",s);
                context.startActivity(in);

            }
        });

        if(i>previousposition){
            Animation.animate(myviewholder,true);
        }
        else {
            Animation.animate(myviewholder,false);

        }
        previousposition=i;

    }

    @Override
    public int getItemCount() {
        return collectdata.size();
    }
public void setfilter(List<showcase> data){
        collectdata=new ArrayList<>();

        collectdata.addAll(data);
        notifyDataSetChanged();
}

    class myviewholder extends RecyclerView.ViewHolder{
        VideoView videoView;YouTubeThumbnailView youTubeThumbnailView;ProgressBar progressBar;
        TextView postid,title;ImageView imageView2,youtube,play;RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
         progressBar=itemView.findViewById(R.id.progressBar4);
            play=itemView.findViewById(R.id.imageViewplay);
            imageView2=itemView.findViewById(R.id.imageView2);
            videoView=itemView.findViewById(R.id.videoView2);
youTubeThumbnailView=itemView.findViewById(R.id.y);
           youtube=itemView.findViewById(R.id.imageViewyb);
            relativeLayout=itemView.findViewById(R.id.r);
title=itemView.findViewById(R.id.textViewtitle);
            postid=itemView.findViewById(R.id.postid);

    }
}}
