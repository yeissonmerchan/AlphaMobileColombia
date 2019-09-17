package com.example.alphamobilecolombia.mvp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;

public class ImagesBackGroundReceiver2 extends BroadcastReceiver {

    Context _Context;

    @Override
    public void onReceive(Context context, Intent intent) {

        _Context = context;

        try {
            if (isOnline(context)) {
                /*Toast.makeText(context, "Regresó el internet", Toast.LENGTH_LONG).show();*/
            } else {
                /*Toast.makeText(context, "Se fue el internet", Toast.LENGTH_LONG).show();*/
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "onReceive", e, context);
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "isOnline", e, _Context);
            return false;
        }
    }
}
