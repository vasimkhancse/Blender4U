package com.example.khanbros.blender4umodel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import ru.whalemare.sheetmenu.SheetMenu;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewActivity extends YouTubeBaseActivity {
ImageLoader imageLoader;VideoView videoView;YouTubePlayerView youTubePlayerView;ImageView imageView2;
ProgressBar progressBar;ImageView fullin,more;Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        videoView=findViewById(R.id.videoView2);
imageView2=findViewById(R.id.imageView2);
       fullin=findViewById(R.id.imageViewfulin);
      more=findViewById(R.id.imageViewmore);
youTubePlayerView=findViewById(R.id.y);
progressBar=findViewById(R.id.progressBar);reqpermisson();
fullin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);


        }
    }
});
        final String s=getIntent().getStringExtra("post");
        if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){

         videoView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.VISIBLE);
    more.setVisibility(View.VISIBLE);
       youTubePlayerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.GONE);

            final String img="http://10.0.2.2:8080/program/"+s;

            imageLoader= ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());
            DisplayImageOptions options= new DisplayImageOptions.Builder()
                    //    .cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.blender).build();

            if(s.startsWith("http")||s.startsWith("con")){
                imageLoader.displayImage(s, imageView2, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        bitmap=loadedImage;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }
            else {
                imageLoader.displayImage(img, imageView2, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        bitmap=loadedImage;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }


            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isexternalstroageavailable()){
                        menu();


                    }

                }
            });
imageView2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(imageView2);
        photoViewAttacher.update();
    }
});


        }
        else if(s.endsWith("mp4")||s.endsWith("3gp")){

            videoView.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            youTubePlayerView.setVisibility(View.INVISIBLE);
fullin.setVisibility(View.VISIBLE);


            final String vide="http://10.0.2.2:8080/program/"+s;

            MediaController mc=new MediaController(this);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            Uri video=Uri.parse(vide);
           videoView.setMediaController(mc);
           videoView.setVideoURI(video);
           videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               @Override
               public void onPrepared(MediaPlayer mp) {
                  progressBar.setVisibility(View.GONE);

                   videoView.start();
               }
           });


        }
        else if(s.startsWith("https://youtu.be")){

         videoView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
           youTubePlayerView.setVisibility(View.VISIBLE);
           youTubePlayerView.initialize("AIzaSyABQYpFQwEc5RAr924e37Wvg1XEKhI_KJI", new YouTubePlayer.OnInitializedListener() {
               @Override
               public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                   progressBar.setVisibility(View.GONE);

                   youTubePlayer.loadVideo(s.substring(17,28));
               }

               @Override
               public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

               }
           });


        }
    }

    private boolean isexternalstroageavailable() {
        String state=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return  true;
        }
        return  false;
    }

    private void menu() {
        SheetMenu.with(this)
                .setMenu(R.menu.save)
                .setAutoCancel(true)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int i=item.getItemId();
                        if(i==R.id.save){
                            FileOutputStream outputStream;
                            File path= Environment.getExternalStorageDirectory();
                            File dir=new File(path.getAbsolutePath()+"/Blender4U");

                                dir.mkdirs();

                           Random r=new Random();
                            File file=new File(dir,"Blender4u"+r.nextInt()+".jpg");

                            try {
                                outputStream=new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                                Toast.makeText(getApplicationContext(),"Image saved to gallery",Toast.LENGTH_LONG).show();
                                outputStream.flush();
                                outputStream.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }



                        return false;
                    }
                }).show();
    }
    private void reqpermisson() {
        int rq= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(rq!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==2&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }
    }
}
