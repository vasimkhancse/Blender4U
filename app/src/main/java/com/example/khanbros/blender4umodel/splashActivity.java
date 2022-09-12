package com.example.khanbros.blender4umodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class splashActivity extends Activity {
GifImageView gifImageView;SharedPreferences sharedPreferences,sharedPreferences2;
boolean open;public  static String k="show";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences2=getSharedPreferences(k, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences2.edit();
        editor.putString(getResources().getString(R.string.no),"0");
        editor.commit();



        gifImageView=findViewById(R.id.i);
        try {
            InputStream inputStream=getAssets().open("blender.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }

       sharedPreferences= getSharedPreferences("prefer",MODE_PRIVATE);
        open=sharedPreferences.getBoolean("open",true);

        if(open){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    open=false;
                    editor.putBoolean("open",open);
                    editor.apply();

                    startActivity(new Intent(splashActivity.this,UserLoginActivity.class));
                }
            },2000);

        }
        else {
            startActivity(new Intent(splashActivity.this,UserLoginActivity.class));

        }
    }
}
