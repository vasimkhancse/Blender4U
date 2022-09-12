package com.example.khanbros.blender4umodel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class mediaActivity extends AppCompatActivity {
    MaterialSearchView searchView;TabLayout tabLayout;ViewPager viewPager;
    pageradapter2 pageradapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.v);
        if(getIntent().getStringExtra("no").equals("0")){

            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("photo"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("video"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_newsfeed).setText("post"));

            pageradapter=new pageradapter2(getSupportFragmentManager(),tabLayout.getTabCount(),0,"");


        }
        else{
             String email=getIntent().getStringExtra("email");
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("photo"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("video"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_newsfeed).setText("post"));
            pageradapter=new pageradapter2(getSupportFragmentManager(),tabLayout.getTabCount(),1,email);
        }

        viewPager.setAdapter(pageradapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0){
                    tabLayout.setBackgroundColor(Color.GRAY);
                }
                if(tab.getPosition()==1){
                    tabLayout.setBackgroundColor(Color.DKGRAY);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
