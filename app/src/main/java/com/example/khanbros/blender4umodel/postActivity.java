package com.example.khanbros.blender4umodel;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class postActivity extends AppCompatActivity {
    ImageLoader imageLoader; RequestQueue requestQueue;String r;String s;
    String posturl="http://10.0.2.2:8080/program/editcontent.php",postid="";
TextView email;EditText epost,title;RelativeLayout linearLayout;ImageView imageView;VideoView videoView;
Button post,image,video;String selectedPath,datetime;ProgressDialog progressDialog;    private static final int SELECT_VIDEO = 3;
    private static final int SELECT_Image = 2;Bitmap bitmap=null;   boolean CheckEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Post");
        email= (TextView) findViewById(R.id.textViewemail);
        email.setText(getIntent().getStringExtra("email"));

       title= (EditText) findViewById(R.id.editTexttitle);
       linearLayout= (RelativeLayout) findViewById(R.id.l);
       imageView= (ImageView) findViewById(R.id.imageView);
       videoView= (VideoView) findViewById(R.id.videoView);
       image= (Button) findViewById(R.id.image);
   video= (Button) findViewById(R.id.video);

reqpermisson();


        Date d=new Date();
        SimpleDateFormat ss=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
        datetime=(ss.format(d));

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
               // Toast.makeText(getApplicationContext(),"ggggg",Toast.LENGTH_SHORT).show();
            }
        });

     String no=getIntent().getStringExtra("no");
       if(no.equals("1")){
           postid=getIntent().getStringExtra("postid");
           title.setText(getIntent().getStringExtra("title"));
         r=getIntent().getStringExtra("title");

           //ithu edit postkku
            s=getIntent().getStringExtra("post");
           if(s.endsWith("jpg")||s.endsWith("png")||s.endsWith("jpeg")){
linearLayout.setVisibility(View.VISIBLE);
videoView.setVisibility(View.GONE);

               final String img="http://10.0.2.2:8080/program/"+s;

               imageLoader= ImageLoader.getInstance();
               imageLoader.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());
               DisplayImageOptions options= new DisplayImageOptions.Builder()
                       //    .cacheInMemory(true).cacheOnDisk(true)
                       .showImageOnLoading(R.drawable.blender).build();


               imageLoader.displayImage(img,imageView,options);
           }
           else if(s.endsWith("mp4")||s.endsWith("3gp")){
               linearLayout.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
               final String vide="http://10.0.2.2:8080/program/"+s;

               // MediaController mc=new MediaController(context);
               // mc.setAnchorView(myviewholder.videoView);
               //   mc.setMediaPlayer(myviewholder.videoView);
               Uri video=Uri.parse(vide);
               // myviewholder.videoView.setMediaController(mc);
               videoView.setVideoURI(video);
               videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                   @Override
                   public void onPrepared(MediaPlayer mp) {

                       videoView.start();
                   }
               });


           }


    }}
    private void reqpermisson() {
        int rq= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(rq!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==2&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }
    }
    private void chooseVideo() {
        s="s";
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);



    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == SELECT_VIDEO ||requestCode == SELECT_Image && data != null && data.getData() != null) {

            //  Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();


            if(requestCode==SELECT_VIDEO){
                Uri selectedVideoUri = data.getData();
linearLayout.setVisibility(View.VISIBLE);
             videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
                try {
                    selectedPath =getPath(this,selectedVideoUri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                videoView.setVideoURI(selectedVideoUri);
                videoView.seekTo(2000);


            }else if(requestCode==SELECT_Image){
                Uri selectedImageUri = data.getData();
                linearLayout.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);

                try {
                    selectedPath =getPath(this,selectedImageUri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }




            }


        }}
    public void CheckEditTextIsEmptyOrNot(){

        String tit = title.getText().toString();





        if(TextUtils.isEmpty(tit)  )
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
    public void view2(final String s, final String i){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                File emai=new File(email.getText().toString());
                File titl=new File(title.getText().toString());
                File no=new File(i);
                File dt=new File(datetime);
                File pid=new File(postid);

                File f=new File(s);
                String filepath=f.getAbsolutePath();
                OkHttpClient clienta=new OkHttpClient();
                String contentype=getmimetype(f.getPath());

                RequestBody fileBody=RequestBody.create(MediaType.parse(contentype),f);

                RequestBody  requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("type",contentype)
                        .addFormDataPart("email",emai.getName())
                        .addFormDataPart("title",titl.getName())
                        .addFormDataPart("postid",pid.getName())
                        .addFormDataPart("date_time",dt.getName())
                        .addFormDataPart("no",no.getName())

                        .addFormDataPart("uploaded_file",filepath.substring(filepath.lastIndexOf("/")+1),fileBody)
                        .build();


                Request request=new Request.Builder()
                        .url("http://10.0.2.2:8080/program/post.php")
                        .post(requestBody)
                        .build();

                try {
                    Response response=clienta.newCall(request).execute();
                    if (!response.isSuccessful()){
                        throw new IOException("error"+response);
                    }progressDialog.dismiss();
             postActivity.this.finish();
                    if(getIntent().getStringExtra("no").equals("1")){
                    Intent intent = new Intent(getApplicationContext(),viewpost.class);
                        intent.putExtra("back","0");
                    intent.putExtra("postid",postid);


                    startActivity(intent);}
                    else{
                        if(getIntent().getStringExtra("no").equals("2")){
                            Intent in=new Intent(postActivity.this,showcaseActivity.class);

                            startActivity(in);
                        }
                        else {
                    Intent in=new Intent(postActivity.this,MainRnewsfeedActivity.class);

                    startActivity(in);}}
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        });
        thread.start();

    }


   /* private String pathimage(Uri selectedImageUri) {
        Cursor cursor =getContentResolver().query(selectedImageUri, null, null, null, null);
        cursor.moveToFirst();
        String image_id = cursor.getString(0);
        image_id = image_id.substring(image_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }*/


    private String getmimetype(String path) {
        String extension= MimeTypeMap.getFileExtensionFromUrl(path);
        return  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

   /* private String pathvideo(Uri uri)
    {

        Cursor cursor =getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String image_id = cursor.getString(0);
        image_id = image_id.substring(image_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();
        return path;

    }*/





    private void chooseImage() {
        s="s";
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a IMage "), SELECT_Image);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        if(i==android.R.id.home){
            this.finish();

        }
        if(i==R.id.post){
            CheckEditTextIsEmptyOrNot();
            if(CheckEditText){
                if(getIntent().getStringExtra("no").equals("1")){
                    if(s.equals("s")){
                        view2(selectedPath,"2");
                        progressDialog =new ProgressDialog(postActivity.this);
                        progressDialog.setTitle("uploading");
                        progressDialog.setMessage("please wait");
                        progressDialog.show();

           }else {
                        post();
                   }
                }
                else {
                view2(selectedPath,"1");
                    progressDialog =new ProgressDialog(postActivity.this);
                    progressDialog.setTitle("uploading");
                    progressDialog.setMessage("please wait");
                    progressDialog.show();
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }
    private void post() {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, posturl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("successfully")){
                    postActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(),viewpost.class);
                    intent.putExtra("back","0");
                    intent.putExtra("postid",postid);


                    startActivity(intent);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("no", "1");

                map.put("title", title.getText().toString());
                map.put("email", email.getText().toString());
                map.put("postid", postid);
                return map;

            }
        };
        requestQueue.add(request);
    }
    public  String getPath(Context context, Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{ split[1] };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}



