package com.example.alphamobilecolombia.utils.crashlytics;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class LogError {
    public static void SendErrorCrashlytics(String nameClass, String identifier, Exception exception, Context context){
        try {
            final Fabric fabric = new Fabric.Builder(context)
                    .kits(new Crashlytics())
                    .build();
            Fabric.with(fabric);

            Fabric.with(context, new Crashlytics());
            Crashlytics.setString(nameClass, identifier);
            Crashlytics.setString(identifier, exception.getMessage());

            Crashlytics.logException(exception);
            //throw new Exception("Send Error Crashlytics");
            Crashlytics.getInstance().crash();
        }
        catch (Exception ex){
            Crashlytics.logException(ex);
            ex.printStackTrace();
        }
    }
}
