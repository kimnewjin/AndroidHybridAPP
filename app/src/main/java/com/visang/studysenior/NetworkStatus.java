package com.visang.studysenior;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {

    public static final int TYPEWIFI = 1;
    public static final int TYPEMOBILE = 2;
    public static final int TYPENONE= 3;


    public static int getConnectivityStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null){
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_MOBILE){
                return TYPEMOBILE;
            }else if(type==ConnectivityManager.TYPE_WIFI){
                return TYPEWIFI;
            }
        }

        return TYPENONE;
    }

}
