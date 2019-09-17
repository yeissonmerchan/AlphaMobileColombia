package com.example.alphamobilecolombia.mvp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.services.implement.FileStorageJob;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.configuration.IFileStorageService;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.notification.local.INotification;

public class ImagesBackGroundReceiver extends BroadcastReceiver {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IUploadFilesPresenter iUploadFilesPresenter;
    IFileStorageService iFileStorageService;
    INotification iNotification;
    Context _Context;
    //
    @Override
    public void onReceive(Context context, Intent intent) {

        _Context = context;
        iUploadFilesPresenter = diContainer.injectDIIUploadFilesPresenter(context);
        iFileStorageService = diContainer.injectIFileStorageService(context);
        iNotification = diContainer.injectINotification(context);
        Toast.makeText(context, "Hola :)", Toast.LENGTH_LONG).show();
        try {
            if (isOnline(context)) {
                Toast.makeText(context, "Regresó el internet", Toast.LENGTH_LONG).show();
                FileStorageJob fileStorageJob = new FileStorageJob(iUploadFilesPresenter, iFileStorageService, iNotification);
                fileStorageJob.SendFilesToStorage();
            } else {
                Toast.makeText(context, "Se fue el internet", Toast.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "onReceive", e, context);
        }

        //if (context != null)
        //context.startService(new Intent(context, ImagesBackgroundService.class));
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



