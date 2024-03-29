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

    private String tickerMap;
    private String contentTitleMap;
    private String contentTextMap;
    private String subTextMap;

    void setNotification(String ticker, String contentTitle, String contentText, String subText, Intent intent, NotificationCompat.Builder builder, Context context,String...params) {

        this.ticker = ticker;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.subText = subText;


        Intent mIntent = new Intent(context, TestActivity.class);
        intent.putExtra("dealsData",params);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, mIntent, 0);

        builder = new NotificationCompat.Builder(context);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.dealertsmall);
        builder.setTicker(ticker);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setLargeIcon(icon);
        builder.setSmallIcon(R.drawable.dealertsmall);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setSubText(subText);
        builder.setContentIntent(pendingIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        //builder.build();

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    void setNotificationMap(String ticker, String contentTitle, String contentText, String subText, Intent intent, NotificationCompat.Builder builder, Context context,double lat,double lon) {

        this.tickerMap = ticker;
        this.contentTitleMap = contentTitle;
        this.contentTextMap = contentText;
        this.subTextMap = subText;

        Uri mapIntent = Uri.parse("google.navigation:q=" +lat + "," +lon );
        Intent mIntent = new Intent(Intent.ACTION_VIEW,mapIntent);
        intent.setPackage("com.google.android.apps.maps");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, mIntent, 0);

        builder = new NotificationCompat.Builder(context);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.dealertsmall);
        builder.setTicker(ticker);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setLargeIcon(icon);
        builder.setSmallIcon(R.drawable.dealertsmall);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setSubText(subText);
        builder.setContentIntent(pendingIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        //builder.build();

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify((int) System.currentTimeMillis(), builder.build());
    }

}