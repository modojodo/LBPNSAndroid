package com.lbpns.android.lbpnsandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Umer on 12/8/2015.
 */
public class Connectivity {

    public static boolean isConnectedToInternet(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public static void noInternetToast(Context context) {
        Toast.makeText(context, R.string.network_not_connected_toast, Toast.LENGTH_SHORT).show();
    }
}
