package com.example.khanbros.blender4umodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class tutorialActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ListView l;
    String title[] = {"Model", "Animation"};
    Context context = tutorialActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Tutorials");
        declare();

    }
    public  void declare(){
        progressBar = findViewById(R.id.progressBar);
        l = findViewById(R.id.l);
        l.setAdapter( new list(context, title));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    startActivity(new Intent(context, viewtutorialActivity.class).putExtra("title",title[position]));

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();

            Intent in = new Intent(this, learnActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent in = new Intent(this, learnActivity.class);
        startActivity(in);
    }


    public class list extends ArrayAdapter {

        String[] title;
        int img[]={R.drawable.emoji_3_3,R.drawable.emoji_4_3};
        public list(Context context, String[] title) {

            super(context, R.layout.options,R.id.textView,title);
            this.title=title;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater= (LayoutInflater)context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=layoutInflater.inflate(R.layout.options,parent,false);

            TextView g=v.findViewById(R.id.textView);
            CircleImageView i=v.findViewById(R.id.imageView);
            i.setImageResource(img[position]);
            g.setText("  "+title[position]);



            return  v;
        }

}}
