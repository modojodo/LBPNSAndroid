package com.lbpns.android.lbpnsandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Asad 15R on 12/31/2015.
 */
public class GeofenceReceiver extends BroadcastReceiver {

    //    private static final double LATITUDEs = 25.020588;
//    private static final double LONGITUDEs = 67.132638;
    private String ticker = "Mighty Zinger Combo!";
    double LATITUDE = 25.020588;
    double LONGITUDE = 67.132638;
    NotificationCompat.Builder builder;

    @Override
    public void onReceive(Context context, Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        boolean state = intent.getBooleanExtra(key, false);
        boolean max = intent.getBooleanExtra("max", false);
        String title = intent.getStringExtra("dealTitle");
        String[] value = intent.getStringArrayExtra("values");
        String content = intent.getStringExtra("dealContent");
        String price = intent.getStringExtra("price");
        double lat = intent.getDoubleExtra("lat",0);
        double lon = intent.getDoubleExtra("lon",0);

        price = "RS. " + price;
        String ticker = title;
        int moreDealsLength = intent.getIntExtra("moreDealsLength", 0);
        if (state) {


            Notification notification = new Notification();
            notification.setNotificationMap(ticker, title, content, price, intent, builder, context,lat,lon);

            if (max) {
                notification.setNotification(ticker, "Hey!! More deals from same restaurant", "There are " + moreDealsLength + " more deals ;)", "Click here to see more!", intent, builder, context,value);
            }


        }

    }
}
