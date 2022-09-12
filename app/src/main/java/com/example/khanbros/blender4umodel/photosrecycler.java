package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

class photosrecycler extends RecyclerView.Adapter<photosrecycler.myviewholder>{
    List<showcase> collectdata;ImageLoader imageLoader;
    Context context;LayoutInflater inflater;String dtime=" ";String email;
    String countlike;String s;
    RequestQueue requestQueue;SharedPreferences sharedPreferences;
    public photosrecycler(Context context, List<showcase> collectdata) {
        this.context=context;
        this.collectdata=collectdata;

        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.photos,viewGroup,false);
        myviewholder myviewholder=new myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder myviewholder, final int i) {

       // myviewholder.title.setText(collectdata.get(i).title);
       // myviewholder.postid.setText(collectdata.get(i).postid);
       final String s=collectdata.get(i).post;
        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){



            final String img="http://10.0.2.2:8080/program/"+s;

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.blender).build();



                imageLoader.displayImage(img,myviewholder.imageView2,options);
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


    }

    @Override
    public int getItemCount() {
        return collectdata.size();
    }


    class myviewholder extends RecyclerView.ViewHolder{

        TextView postid,title;ImageView imageView2,youtube,play;RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);


            imageView2=itemView.findViewById(R.id.imageView2);


            relativeLayout=itemView.findViewById(R.id.r);



    }
}}
