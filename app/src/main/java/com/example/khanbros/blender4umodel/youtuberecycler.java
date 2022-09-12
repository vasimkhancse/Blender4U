package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class youtuberecycler extends RecyclerView.Adapter<youtuberecycler.youtube> {

    ArrayList<youtubedata> data;int previousposition=0;
    Context context;ImageLoader imageLoader;int y;

    public youtuberecycler(ArrayList<youtubedata> data, Context context,int y) {
        this.data = data;this.y=y;
        this.context = context;
    }

    @Override
    public youtube onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube,parent,false);
        youtube y=new youtube(v);
        return y;
    }

    @Override
    public void onBindViewHolder(final youtube holder, final int i) {

        if(i>previousposition){
            Animation.animate(holder,true);
        }
        else {
            Animation.animate(holder,false);

        }
        previousposition=i;
        if(y==0){

      holder.title.setText(data.get(i).getTitle());
        holder.desc.setText(data.get(i).getDesc());
        holder.id.setText(data.get(i).getId());
        imageLoader= ImageLoader.getInstance();
        imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
        DisplayImageOptions options= new DisplayImageOptions.Builder()
                //    .cacheInMemory(true).cacheOnDisk(true)
               // .showImageOnLoading(R.drawable.blender)
         .build();


        imageLoader.displayImage(data.get(i).getImage(), holder.imageView,options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }
        });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String y="https://youtu.be/"+data.get(i).getId();
                    Intent in=new Intent(context,ViewActivity.class);
                    in.putExtra("post",y);
                    context.startActivity(in);

                }
            });


        }
        else {
            holder.relativeLayout.setVisibility(View.GONE);
            String img_url="http://img.youtube.com/vi/"+data.get(i).getId().substring(17,28)+"/0.jpg";
            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .build();

            imageLoader.displayImage(img_url, holder.imageView2, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                   holder.progressBar2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                   holder.progressBar2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.progressBar2.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    holder.progressBar2.setVisibility(View.VISIBLE);
                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String y=data.get(i).getId();
                    Intent in=new Intent(context,ViewActivity.class);
                    in.putExtra("post",y);
                    context.startActivity(in);

                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class youtube extends RecyclerView.ViewHolder{
        CardView cardView;RelativeLayout relativeLayout;
        TextView title,desc,id;ImageView imageView,imageView2;ProgressBar progressBar,progressBar2;
        public youtube(View itemView) {
            super(itemView);
            imageView2=itemView.findViewById(R.id.y);
            relativeLayout=itemView.findViewById(R.id.o);
            cardView=itemView.findViewById(R.id.c);
            desc=itemView.findViewById(R.id.desc);
            progressBar=itemView.findViewById(R.id.p);
            progressBar2=itemView.findViewById(R.id.p2);
            title=itemView.findViewById(R.id.title);
            id=itemView.findViewById(R.id.yid);
          imageView=  itemView.findViewById(R.id.imageView3);
        }
    }
}
