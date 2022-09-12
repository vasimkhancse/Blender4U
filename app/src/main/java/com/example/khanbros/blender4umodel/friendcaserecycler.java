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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class friendcaserecycler extends RecyclerView.Adapter<friendcaserecycler.myviewholder>{
    List<friendpostinfo> collectdata;ImageLoader imageLoader;int previousposition=0;
    Context context;LayoutInflater inflater;String email;String followposturl="http://10.0.2.2:8080/program/followpost.php";
    String countlike;int s;  String  likesurl="http://10.0.2.2:8080/program/follows.php";
    RequestQueue requestQueue;SharedPreferences sharedPreferences;
    public friendcaserecycler(Context context, List<friendpostinfo> collectdata,int s) {
        this.context=context;
        this.collectdata=collectdata;
        this.s=s;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.users,viewGroup,false);
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


        sharedPreferences=context.getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        email=  sharedPreferences.getString(context.getResources().getString(R.string.email),"");
       if(s==1){
           myviewholder.follow.setVisibility(View.GONE);
       }
        if(s==2){
                 likeshow(collectdata.get(i).email,myviewholder.follow,myviewholder.following);
        }
        myviewholder.name.setText(collectdata.get(i).name);

       final String v=collectdata.get(i).imagepath;


            final String img="http://10.0.2.2:8080/program/"+v;

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.blender).build();

        imageLoader.displayImage(img,myviewholder.imageView,options);

        myviewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(context, friendprofileActivity.class);
                in.putExtra("email", collectdata.get(i).email);

                context.startActivity(in);
            }

        });
myviewholder.follow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        follow(myviewholder,i,1,collectdata.get(i).name,collectdata.get(i).imagepath,collectdata.get(i).email);
    }
});
        myviewholder.following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow(myviewholder,i,0,collectdata.get(i).name,collectdata.get(i).imagepath,collectdata.get(i).email);
            }
        });
    }

    private void follow(final myviewholder myviewholder, int i, final int no, final String name, final String imagepath, final String Email) {
        requestQueue= Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST,followposturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


                if(response.equalsIgnoreCase("follow")) {
                    myviewholder.following.setVisibility(View.GONE);
                    myviewholder.follow.setVisibility(View.VISIBLE);
                }
                else if(response.equalsIgnoreCase("following")){
                   myviewholder. follow.setVisibility(View.GONE);
                    myviewholder.following.setVisibility(View.VISIBLE);
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
                map.put("following",Email );
                map.put("name",name);
                map.put("img",imagepath);
                map.put("follow",email);
                return map;

            }
        };
        requestQueue.add(request);
    }
    private void likeshow(final String follower, final Button like, final Button unlike) {
        requestQueue= Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.POST, likesurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //   Toast.makeText(context,response,Toast.LENGTH_SHORT).show();


                if(response.equalsIgnoreCase("follow")){

                    like.setVisibility(View.INVISIBLE);
                    unlike.setVisibility(View.VISIBLE);
                }
                else if(response.equalsIgnoreCase("unfollow")){
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

                map.put("follower", follower);
                map.put("email",email);
                return map;

            }
        };
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return collectdata.size();
    }


    class myviewholder extends RecyclerView.ViewHolder{
        Button follow,following;CardView cardView;
        TextView name;CircleImageView imageView;RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.l);
            imageView=itemView.findViewById(R.id.rr);
            follow=itemView.findViewById(R.id.button6);
            following=itemView.findViewById(R.id.button7);

            relativeLayout=itemView.findViewById(R.id.r);
name=itemView.findViewById(R.id.textViewname);


    }
}}
