package com.example.alphamobilecolombia.utils;

import android.content.ComponentName;
import android.content.Context;

import com.example.alphamobilecolombia.data.cloud.firestore.ICloudStoreInstance;
import com.example.alphamobilecolombia.data.cloud.firestore.implement.CloudStoreInstance;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.data.remote.instance.implement.MapRequest;
import com.example.alphamobilecolombia.data.remote.instance.implement.RetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.adapter.ILoginAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IPersonAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IUploadFileAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IVersionUpdateAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.CreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.LoginAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.PersonAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.UploadFileAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.VersionUpdateAdapter;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.ILoginPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IPersonPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IVersionUpdatePresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.CreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.LoginPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.PersonPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.UploadFilesPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.VersionUpdatePresenter;
import com.example.alphamobilecolombia.utils.cryptography.implement.MD5Hashing;
import com.example.alphamobilecolombia.utils.cryptography.providers.IMD5Hashing;
import com.example.alphamobilecolombia.utils.files.IFileStorage;
import com.example.alphamobilecolombia.utils.files.implement.FileStorage;

public class DependencyInjectionContainer {
    //Start Presenters
    public IUploadFilesPresenter injectDIIUploadFilesPresenter(Context context){
        return new UploadFilesPresenter(injectDIIUploadFileAdapter(context),injectIFileStorage(context),context);
    }

    public ILoginPresenter injectDIILoginPresenter(Context context){
        return new LoginPresenter(context,injectDIILoginAdapter(context),injectIMD5Hashing(),injectICloudStoreInstance(context));
    }

    public IVersionUpdatePresenter injectDIIVersionUpdatePresenter (Context context){
        return new VersionUpdatePresenter(context,injectDIIVersionUpdateAdapter(context));
    }

    public ICreditSubjectPresenter injectDIICreditSubjectPresenter(Context context)
    {
        return new CreditSubjectPresenter(injectDIICreditSubjectAdapter(context),context);
    }

    public IPersonPresenter injectDIIPersonPresenter(Context context)
    {
        return new PersonPresenter(injectDIIPersonAdapter(context),context);
    }
    //End Presenters

    //Start Adapters
    private IUploadFileAdapter injectDIIUploadFileAdapter(Context context){
        return new UploadFileAdapter(injectIRetrofitInstance(),injectIMapRequest(),context);
    }

    private IVersionUpdateAdapter injectDIIVersionUpdateAdapter (Context context){
        return new VersionUpdateAdapter(injectIRetrofitInstance(),injectIMapRequest(),context);
    }

    private ILoginAdapter injectDIILoginAdapter(Context context)
    {
        return new LoginAdapter(injectIRetrofitInstance(),injectIMapRequest(),context);
    }

    private ICreditSubjectAdapter injectDIICreditSubjectAdapter(Context context)
    {
        return new CreditSubjectAdapter(injectIRetrofitInstance(),injectIMapRequest(),context);
    }

    private IPersonAdapter injectDIIPersonAdapter(Context context)
    {
        return new PersonAdapter(injectIRetrofitInstance(),injectIMapRequest(),context);
    }
    //End Adapters

    //Start Configurations
    private ICloudStoreInstance injectICloudStoreInstance(Context context){
        return new CloudStoreInstance(context);
    }

    private IRetrofitInstance injectIRetrofitInstance(){
        return new RetrofitInstance();
    }

    private IMapRequest injectIMapRequest(){
        return new MapRequest();
    }

    private IMD5Hashing injectIMD5Hashing(){
        return new MD5Hashing();
    }

    private FileStorage injectIFileStorage(Context context){ return new FileStorage(context);}
    //End Configurations
}
