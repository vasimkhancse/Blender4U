package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

class updaterecycler extends RecyclerView.Adapter<updaterecycler.myviewholder>{
    List<commentpostinfo> collectdata;ImageLoader imageLoader;
    Context context;LayoutInflater inflater;String dtime=" ";String email;

    RequestQueue requestQueue;SharedPreferences sharedPreferences;ArrayList<String> likelist;

    public updaterecycler(Context context, List<commentpostinfo> collectdata) {
        this.context=context;
        this.collectdata=collectdata;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.options,viewGroup,false);
        myviewholder myviewholder=new myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder myviewholder, final int i) {

        myviewholder.title.setText(collectdata.get(i).comment);

        myviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, webActivity.class);
                in.putExtra("url",collectdata.get(i).link);
                context.startActivity(in);
            }
        });

        //ithu imagekku
        final String photo = "http://10.0.2.2:8080/program/" + collectdata.get(i).imagepath;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //    .cacheInMemory(true).cacheOnDisk(true)
                .showImageOnLoading(R.drawable.blender).build();


        if (collectdata.get(i).imagepath.startsWith("http") || collectdata.get(i).imagepath.startsWith("con")) {
            imageLoader.displayImage(collectdata.get(i).imagepath, myviewholder.imageView, options);
        } else {
            imageLoader.displayImage(photo, myviewholder.imageView, options);
        }



    }

    @Override
    public int getItemCount() {
        return collectdata.size();
    }


    class myviewholder extends RecyclerView.ViewHolder{
        TextView title;CircleImageView imageView;CardView cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);

            cardView=itemView.findViewById(R.id.r);

           title=itemView.findViewById(R.id.textView);
           cardView.setRadius(20);
        }
    }
}
