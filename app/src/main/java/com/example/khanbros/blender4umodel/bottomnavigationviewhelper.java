package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class bottomnavigationviewhelper {

        public bottomnavigationviewhelper(BottomNavigationViewEx bottomNavigationViewEx) {

                bottomNavigationViewEx.enableAnimation(false);
                bottomNavigationViewEx.enableItemShiftingMode(false);
                bottomNavigationViewEx.enableShiftingMode(false);

                //text visible seiya
              //  bottomNavigationViewEx.setTextVisibility(false);


        }


        public bottomnavigationviewhelper(final Context context, final BottomNavigationViewEx bottomNavigationViewEx){
            bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.icquestion:
                            Intent intent=new Intent(context,QuestionActivity.class);

                            context.startActivity(intent);
                            return true;
                        case R.id.iclearn:
                            Intent intent2=new Intent(context,learnActivity.class);

                            context.startActivity(intent2);

                            return true;
                        case R.id.icnews:
                            Intent intent3=new Intent(context,MainRnewsfeedActivity.class);
                             context.startActivity(intent3);
                            return true;
                        case R.id.icshowcase:
                            Intent intent4=new Intent(context,showcaseActivity.class);

                            context.startActivity(intent4);
                            return true;
                        case R.id.icprofile:
                            Intent intent5=new Intent(context,profileActivity.class);

                            context.startActivity(intent5);
                            return true;
                    }


                    return false;
                }
            });
        }}


