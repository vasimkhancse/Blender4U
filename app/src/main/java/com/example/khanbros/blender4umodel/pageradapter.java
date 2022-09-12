package com.example.khanbros.blender4umodel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class pageradapter extends FragmentStatePagerAdapter {
    int no;int size;String email;
    public pageradapter(FragmentManager fm,int size,int no,String email) {
        super(fm);this.email=email;
        this.size=size;this.no=no;
    }

    @Override
    public Fragment getItem(int i) {
        if (no == 0) {
            switch (i) {
                case 0:
                   publicFragment p= new publicFragment();
                    Bundle b=new Bundle();
                    b.putString("email",email);
                    b.putString("no","1");
                    p.setArguments(b);
                    return p;
                case 1:
                    userFragment pp= new userFragment();
                    Bundle bb=new Bundle();
                    bb.putString("no","1");
                    bb.putString("email",email);
                    pp.setArguments(bb);
                    return pp;
                case 2:
                  user2Fragment v= new user2Fragment();
                    Bundle c=new Bundle();
                    c.putString("no","1");
                    c.putString("email",email);
                    v.setArguments(c);
                    return v;

            }
        }
        else{switch (i) {
                case 0:
                    userFragment pp= new userFragment();
                    Bundle bb=new Bundle();
                    bb.putString("no","0");
                    bb.putString("email",email);
                    pp.setArguments(bb);
                    return pp;
                case 1:
                    user2Fragment v= new user2Fragment();
                    Bundle c=new Bundle();
                    c.putString("no","0");
                    c.putString("email",email);
                    v.setArguments(c);
                    return v;
        }}
        return null;
    }

    @Override
    public int getCount() {
        return size;
    }
}
