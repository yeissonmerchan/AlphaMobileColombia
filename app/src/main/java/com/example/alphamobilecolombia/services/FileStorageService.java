package com.example.alphamobilecolombia.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.services.implement.FileStorageJob;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.configuration.IFileStorageService;
import com.example.alphamobilecolombia.utils.notification.local.INotification;

public class FileStorageService extends Service {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IUploadFilesPresenter iUploadFilesPresenter;
    IFileStorageService iFileStorageService;
    INotification iNotification;

    public FileStorageService() {
        iUploadFilesPresenter = diContainer.injectDIIUploadFilesPresenter(this);
        iFileStorageService = diContainer.injectIFileStorageService(this);
        iNotification = diContainer.injectINotification(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        StartService("B");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        StartService("C");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Restart |    ", "onStartCommand callback called");
        StartService("SC");
        return super.onStartCommand(intent, flags, startId);
    }

    public void StartService(String process){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    Log.d("service", "onStartCommand callback called");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // your logic that service will perform will be placed here
                FileStorageJob fileStorageJob = new FileStorageJob(iUploadFilesPresenter, iFileStorageService, iNotification);
                fileStorageJob.SendFilesToStorage();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        StartService("D");
        super.onDestroy();
        //Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
    }
}
