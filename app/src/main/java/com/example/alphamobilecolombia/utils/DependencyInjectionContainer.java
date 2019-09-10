package com.example.alphamobilecolombia.utils;

import android.content.ComponentName;
import android.content.Context;

import com.example.alphamobilecolombia.data.cloud.firestore.ICloudStoreInstance;
import com.example.alphamobilecolombia.data.cloud.firestore.implement.CloudStoreInstance;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.implement.RealmInstance;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IProxyService;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.data.remote.instance.implement.MapRequest;
import com.example.alphamobilecolombia.data.remote.instance.implement.ProxyService;
import com.example.alphamobilecolombia.data.remote.instance.implement.RetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.adapter.ILoginAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IPersonAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IProxyServiceAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IUploadFileAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IVersionUpdateAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.CreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.LoginAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.PersonAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.ProxyServiceAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.UploadFileAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.VersionUpdateAdapter;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.ILoginPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IModulePresenter;
import com.example.alphamobilecolombia.mvp.presenter.IPersonPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IProcessCompletedPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IVersionUpdatePresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.CreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.LoginPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.ModulePresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.PersonPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.ProcessCompletedPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.UploadFilesPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.VersionUpdatePresenter;
import com.example.alphamobilecolombia.utils.configuration.IDevice;
import com.example.alphamobilecolombia.utils.configuration.IParameterField;
import com.example.alphamobilecolombia.utils.configuration.ISelectList;
import com.example.alphamobilecolombia.utils.configuration.implement.Device;
import com.example.alphamobilecolombia.utils.configuration.implement.ParameterField;
import com.example.alphamobilecolombia.utils.configuration.implement.SelectList;
import com.example.alphamobilecolombia.utils.cryptography.implement.MD5Hashing;
import com.example.alphamobilecolombia.utils.cryptography.implement.RSA;
import com.example.alphamobilecolombia.utils.cryptography.providers.IMD5Hashing;
import com.example.alphamobilecolombia.utils.cryptography.providers.IRSA;
import com.example.alphamobilecolombia.utils.files.IFileStorage;
import com.example.alphamobilecolombia.utils.files.implement.FileStorage;
import com.example.alphamobilecolombia.utils.notification.local.INotification;
import com.example.alphamobilecolombia.utils.notification.local.implement.Notification;
import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;
import com.example.alphamobilecolombia.utils.security.IAccessToken;
import com.example.alphamobilecolombia.utils.security.implement.AccessToken;

public class DependencyInjectionContainer {
    //Start Presenters
    public IUploadFilesPresenter injectDIIUploadFilesPresenter(Context context){
        return new UploadFilesPresenter(injectDIIUploadFileAdapter(context),injectIFileStorage(context),context);
    }

    public IModulePresenter injectDIIModulePresenter(Context context){
        return new ModulePresenter(injectIParameterField(context));
    }

    public IProcessCompletedPresenter injectDIIProcessCompletedPresenter(Context context){
        return new ProcessCompletedPresenter(injectIParameterField(context));
    }

    public ILoginPresenter injectDIILoginPresenter(Context context){
        return new LoginPresenter(context,injectDIILoginAdapter(context),injectIMD5Hashing(),injectICloudStoreInstance(context),injectIRealmInstance(context),injectIAccessToken(context));
    }

    public IVersionUpdatePresenter injectDIIVersionUpdatePresenter (Context context){
        return new VersionUpdatePresenter(context,injectDIIVersionUpdateAdapter(context),injectICloudStoreInstance(context));
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
        return new UploadFileAdapter(injectIRetrofitInstance(context),injectIMapRequest(context),context);
    }

    private IVersionUpdateAdapter injectDIIVersionUpdateAdapter (Context context){
        return new VersionUpdateAdapter(injectIRetrofitInstance(context),injectIMapRequest(context),context);
    }

    private ILoginAdapter injectDIILoginAdapter(Context context)
    {
        return new LoginAdapter(injectIRetrofitInstance(context),injectIMapRequest(context),injectIDevice(),context);
    }

    private ICreditSubjectAdapter injectDIICreditSubjectAdapter(Context context)
    {
        return new CreditSubjectAdapter(injectIRetrofitInstance(context),injectIMapRequest(context),context);
    }

    private IPersonAdapter injectDIIPersonAdapter(Context context)
    {
        return new PersonAdapter(injectIRetrofitInstance(context),injectIMapRequest(context),context);
    }
    //End Adapters

    //Start Configurations
    private ICloudStoreInstance injectICloudStoreInstance(Context context){
        return new CloudStoreInstance(injectINotification(context), injectIRealmInstance(context),context);
    }

    public ISelectList injectISelectList(Context context){return new SelectList(injectIRealmInstance(context));}

    public IParameterField injectIParameterField(Context context){return new ParameterField(injectIRealmInstance(context));}

    private IRetrofitInstance injectIRetrofitInstance(Context context){ return new RetrofitInstance(injectIAccessToken(context)); }

    //private IProxyServiceAdapter injectIProxyServiceAdapter(Context context){return new ProxyServiceAdapter(injectIRealmInstance(context),injectIRetrofitInstance(context),injectIMapRequest(context),context); }

    //private IProxyService injectIProxyService(Context context) { return new ProxyService(injectIProxyServiceAdapter(context),injectIAccessToken(context));}

    private IMapRequest injectIMapRequest(Context context){ return new MapRequest(null); }

    private IMD5Hashing injectIMD5Hashing(){
        return new MD5Hashing();
    }

    private FileStorage injectIFileStorage(Context context){ return new FileStorage(context);}

    private IDevice injectIDevice(){return new Device();}

    private INotification injectINotification(Context context){
        return new Notification(context);
    }

    private IRSA injectIRSA(Context context){return new RSA(context);}

    private IRealmInstance injectIRealmInstance(Context context){return new RealmInstance(context,injectIRSA(context)); }

    private IAccessToken injectIAccessToken(Context context){return new AccessToken(injectIRealmInstance(context));}
    //End Configurations
}
