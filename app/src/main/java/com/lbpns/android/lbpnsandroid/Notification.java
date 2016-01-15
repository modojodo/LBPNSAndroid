package com.lbpns.android.lbpnsandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Asad 15R on 1/5/2016.
 */
public class Notification {

    private String ticker;
    private String contentTitle;
    private String contentText;
    private String subText;

    void setNotification(String ticker,String contentTitle,String contentText,String subText,double LATITUDE,double LONGITUDE,Intent intent,NotificationCompat.Builder builder,Context context){

        this.ticker = ticker;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.subText = subText;

//        Uri mapIntent = Uri.parse("google.navigation:q=" +LATITUDE + "," +LONGITUDE );
//        Intent mIntent = new Intent(Intent.ACTION_VIEW,mapIntent);
//        intent.setPackage("com.google.android.apps.maps");


//        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,mIntent,0);

        builder = new NotificationCompat.Builder(context);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.burger);
        builder.setTicker("Mighty Zinger Combo!");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setLargeIcon(icon);
        builder.setSmallIcon(R.drawable.burger);
        builder.setContentTitle("Mighty Zinger Combo");
        builder.setContentText("Mighty zinger, regular fries &");
        builder.setSubText("300 ml drink at Rs.580");
//        builder.setContentIntent(pendingIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        //builder.build();

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify((int)System.currentTimeMillis(), builder.build());
    }


}