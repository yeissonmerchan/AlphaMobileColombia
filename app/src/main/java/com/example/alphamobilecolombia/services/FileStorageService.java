package com.example.alphamobilecolombia.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        FileStorageJob fileStorageJob = new FileStorageJob(iUploadFilesPresenter,iFileStorageService,iNotification);
        fileStorageJob.SendFilesToStorage();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Invoke background service onStartCommand method.", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
    }
}
