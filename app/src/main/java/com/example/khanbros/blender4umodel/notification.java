package com.example.khanbros.blender4umodel;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.blender);
        Intent intent1=new Intent(context,MainRnewsfeedActivity.class);
        PendingIntent pendingIntent2=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n=new NotificationCompat.Builder(context)
                .setContentTitle("Blender4U")
                .setContentText("Check it new Posts")
                .setAutoCancel(true).setLargeIcon(bitmap)
                .setContentIntent(pendingIntent2).setSmallIcon(R.drawable.blender);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE) ;

        notificationManager.notify(0,n.build());
    }
}
