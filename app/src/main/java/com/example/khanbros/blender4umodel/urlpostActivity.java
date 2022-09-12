package com.example.khanbros.blender4umodel;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class urlpostActivity extends AppCompatActivity {
TextView email;EditText title,epost;Button post,text,link;String datetime;RequestQueue requestQueue;
    String posturl="http://10.0.2.2:8080/program/url.php"; boolean CheckEditText;String emailHolder;
    ProgressBar progressBar;
RelativeLayout relativeLayout;ImageView imageView;VideoView videoView;SharedPreferences sharedPreferences;
ImageLoader imageLoader;Uri uri=null;String selectedPath;String etext,t;Bitmap bitmap=null;YouTubeThumbnailView youTubeThumbnailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urllinkpost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Post");
        progressBar=findViewById(R.id.progressBar5);
        youTubeThumbnailView=findViewById(R.id.y);
        email = (TextView) findViewById(R.id.textViewemail);
        sharedPreferences = getSharedPreferences(UserLoginActivity.myprefer, Context.MODE_PRIVATE);
        emailHolder = sharedPreferences.getString(getResources().getString(R.string.email), "");
        email.setText(emailHolder);
        relativeLayout = findViewById(R.id.l);
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);

        title = (EditText) findViewById(R.id.editTexttitle);
        epost = (EditText) findViewById(R.id.editTextpost);


        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        datetime = (s.format(d));
         reqpermisson();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            etext=intent.getStringExtra(Intent.EXTRA_TEXT);
            if (type.equals("text/plain")) {
                epost.setText(etext);
                relativeLayout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
               final String ss=epost.getText().toString();
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                youTubeThumbnailView.initialize("AIzaSyABQYpFQwEc5RAr924e37Wvg1XEKhI_KJI", new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        youTubeThumbnailLoader.setVideo(ss.substring(17,28));
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                progressBar.setVisibility(View.GONE);
                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                //  youTubeThumbnailView.setImageResource(R.drawable.blender);
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        youTubeThumbnailView.setImageResource(R.drawable.blender);

                    }
                });

            } else if (type.startsWith("image/")) {
             epost.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);

                imageLoader = ImageLoader.getInstance();
                imageLoader.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        //    .cacheInMemory(true).cacheOnDisk(true)
                        .showImageOnLoading(R.drawable.blender).build();


                imageLoader.displayImage(uri.toString(), imageView, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        progressBar.setVisibility(View.GONE);
                        bitmap=loadedImage;
                      image(bitmap);

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });


                try {
                    selectedPath = getPath(this,uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (type.startsWith("video/")) {
              epost.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);

                videoView.setVideoURI(uri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        progressBar.setVisibility(View.GONE);
                        videoView.seekTo(2000);
                    }
                });

                try {
                    selectedPath = getPath(this,uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }
        }

    }

    private String image(Bitmap loadedImage) {
        bitmap=loadedImage;
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,b);
        byte[]bytes=b.toByteArray();
        return android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);

    }

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


    public void view2(final String s, final String i){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                File emai=new File(email.getText().toString());
                File titl=new File(title.getText().toString());
                File no=new File(i);
                File dt=new File(datetime);


                File f=new File(s);
                String filepath=f.getAbsolutePath();
                OkHttpClient clienta=new OkHttpClient();
                String contentype=getmimetype(f.getPath());

                RequestBody fileBody=RequestBody.create(MediaType.parse(contentype),f);

                RequestBody  requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("type",contentype)
                        .addFormDataPart("email",emai.getName())
                        .addFormDataPart("title",titl.getName())

                        .addFormDataPart("date_time",dt.getName())
                        .addFormDataPart("no",no.getName())

                        .addFormDataPart("uploaded_file",filepath.substring(filepath.lastIndexOf("/")+1),fileBody)
                        .build();


                okhttp3.Request request=new okhttp3.Request.Builder()
                        .url("http://10.0.2.2:8080/program/url.php")
                        .post(requestBody)
                        .build();

                try {
                    okhttp3.Response response=clienta.newCall(request).execute();
                    if (!response.isSuccessful()){
                        throw new IOException("error"+response);
                    }
                   urlpostActivity.this.finish();

                        Intent in=new Intent(urlpostActivity.this,MainRnewsfeedActivity.class);

                        startActivity(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        });
        thread.start();

    }


  /*  private String pathimage(Uri selectedImageUri) {
        String wholeID = DocumentsContract.getDocumentId(selectedImageUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // Where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
                new String[] { id }, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }

        cursor.close();
        return filePath;
    }
*/

    private String getmimetype(String path) {
        String extension= MimeTypeMap.getFileExtensionFromUrl(path);
        return  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

  /*  private String pathvideo(Uri uri)
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

    private void post() {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("success")){
                    urlpostActivity.this.finish();
                    Intent in=new Intent(urlpostActivity.this,MainRnewsfeedActivity.class);

                    startActivity(in);
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
                map.put("title", title.getText().toString());
                map.put("no", String.valueOf(2));
                map.put("date_time", datetime);
                map.put("email", email.getText().toString());
                map.put("post",epost.getText().toString());

                return map;

            }
        };
        requestQueue.add(request);
    }
    public void post(final String s) {
        requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equalsIgnoreCase("success")){
               urlpostActivity.this.finish();
                    Intent in=new Intent(urlpostActivity.this,MainRnewsfeedActivity.class);

                    startActivity(in);
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
                map.put("title", title.getText().toString());
                map.put("no", String.valueOf(3));
                map.put("date_time", datetime);
                map.put("email", email.getText().toString());
                map.put("post",s);

                return map;

            }
        };
        requestQueue.add(request);
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


                if(epost.getVisibility()== View.VISIBLE) {
                    post();
                }
                else if(imageView.getVisibility()==View.VISIBLE) {
                 if(uri.toString().startsWith("content://com.android.providers")){
                     view2(selectedPath,"1");
                 }else {
                     String s=image(bitmap);

                     post(s);
                 }



                }

                else if(videoView.getVisibility()==View.VISIBLE){


                view2(selectedPath,"1");
                }



            }
            else {

                Toast.makeText(getApplicationContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();

            }}
        return super.onOptionsItemSelected(item);
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
