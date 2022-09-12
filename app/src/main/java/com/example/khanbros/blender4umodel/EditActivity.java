package com.example.khanbros.blender4umodel;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class EditActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private static final int SELECT_VIDEO = 3;
    private static final int SELECT_Image = 2;
    String selectedPath;
CircleImageView circleImageView;EditText skill,contents,description;
Button save;TextView edit,email;RequestQueue requestQueue;ImageLoader imageLoader;
    String viewurl="http://10.0.2.2:8080/program/view.php";Context context=EditActivity.this;
    String editurl="http://10.0.2.2:8080/program/editprofile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar=(Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);

getSupportActionBar().setDisplayHomeAsUpEnabled(true);
getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Edit Profile");

        declare();
        reqpermisson();
        showvolley();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgchange();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, editurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        if(response.equalsIgnoreCase("data insert successfully")){
                            finish();
                            Intent intent = new Intent(context,profileActivity.class);

                            startActivity(intent);
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
                        map.put("skills", skill.getText().toString());
                        map.put("content", contents.getText().toString());
                        map.put("description", description.getText().toString());
                        map.put("email", email.getText().toString());
                        return map;

                    }
                };
                requestQueue.add(request);
            }
        });
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
imgchange();
        }
    }

    private void imgchange() {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a Image "), SELECT_Image);
        progressDialog =new ProgressDialog(EditActivity.this);
        progressDialog.setTitle("uploading");
        progressDialog.setMessage("please wait");
        progressDialog.show();

        }



    @Override
    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {

        if (resultCode == RESULT_OK ||requestCode == SELECT_Image ) {

            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {


                        Uri selectedImageUri = data.getData();
                    try {
                        selectedPath = getPath(context,selectedImageUri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    File f=new File(selectedPath);
                    String filepath=f.getAbsolutePath();
                    OkHttpClient clienta=new OkHttpClient();
                    String contentype=getmimetype(f.getPath());

                    RequestBody fileBody=RequestBody.create(MediaType.parse(contentype),f);
                         File file= new File(email.getText().toString());
                    RequestBody  requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("type",contentype)
                            .addFormDataPart("email",file.getName())

                            .addFormDataPart("uploaded_file",filepath.substring(filepath.lastIndexOf("/")+1),fileBody)
                            .build();


                    okhttp3.Request request=new okhttp3.Request.Builder()
                            .url("http://10.0.2.2:8080/program/imgchange.php")
                            .post(requestBody)
                            .build();

                    try {
                        okhttp3.Response response=clienta.newCall(request).execute();
                        if (!response.isSuccessful()){
                            throw new IOException("error"+response);
                        }

                        showvolley();
                        progressDialog.dismiss();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
            });
            thread.start();

        }


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


    public String getmimetype(String path) {
        String extension= MimeTypeMap.getFileExtensionFromUrl(path);
        return  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
            Intent intent = new Intent(context,profileActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showvolley() {
        requestQueue= Volley.newRequestQueue(getApplicationContext());



        StringRequest request = new StringRequest(Request.Method.POST, viewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    JSONArray posts = res.getJSONArray("post");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);

                        String content = post.getString("content");
                        String description2 = post.getString("description");
                        String profileimg = post.getString("profileimg");
                        String photo="http://10.0.2.2:8080/program/"+profileimg;
                        String skills = post.getString("skills");

                        skills=skills.replace("."," ");
                        content=content.replace("."," ");
                        description2=description2.replace("."," ");
                        skill.setText(skills);
                        contents.setText(content);
                        description.setText(description2);
                        imageLoader= ImageLoader.getInstance();
                        imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
                        DisplayImageOptions options= new DisplayImageOptions.Builder()
                                .cacheInMemory(true).cacheOnDisk(true).showImageOnLoading(R.drawable.blender).build();
                   if(profileimg.startsWith("http")){
                       imageLoader.displayImage(profileimg,circleImageView,options);
                   }
                          else {
                       imageLoader.displayImage(photo,circleImageView,options);
                   }

                        // Picasso.get().load(photo).into(imageView);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

                map.put("email", email.getText().toString());
                return map;

            }
        };
        requestQueue.add(request);


    }

    private void declare() {
        circleImageView= (CircleImageView) findViewById(R.id.circleImageView);
        skill= (EditText) findViewById(R.id.editText);
        contents= (EditText) findViewById(R.id.editText2);
        description= (EditText) findViewById(R.id.editText3);
        save= (Button) findViewById(R.id.button2);
        edit= (TextView) findViewById(R.id.tedit);
        email= (TextView) findViewById(R.id.emailtt);
       String emai= getIntent().getStringExtra("email");
       email.setText(emai);
    }
}
