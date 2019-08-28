package com.example.alphamobilecolombia.utils.configuration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.example.alphamobilecolombia.data.local.implement.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.mvp.activity.LoginActivity;
import com.example.alphamobilecolombia.mvp.activity.VersionUpdateActivity;
import com.example.alphamobilecolombia.mvp.presenter.implement.VersionUpdatePresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.firebase.analytics.FirebaseAnalytics;

public class VersionUpdate {
    String version;
    FirebaseAnalytics mFireBaseAnalytics;
    public void Check(Context context){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),version,ex,context);
        }

        VersionUpdatePresenter presenter = new VersionUpdatePresenter(context, null);
        //HttpResponse model = presenter.GetVersion(version,context);
        HttpResponse model = new HttpResponse();

        if (model.getCode().contains("200")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RealmStorage storage = new RealmStorage();
                    storage.initLocalStorage(context.getApplicationContext());
                    mFireBaseAnalytics = FirebaseAnalytics.getInstance(context.getApplicationContext());
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            },2000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ApplicationData applicationData = new ApplicationData();
                    applicationData.RestartNewVersionApp(context.getApplicationContext());

                    Intent intent = new Intent(context, VersionUpdateActivity.class);
                    intent.putExtra("MessageError", model.getMessage());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            },2000);
        }
    }
}
