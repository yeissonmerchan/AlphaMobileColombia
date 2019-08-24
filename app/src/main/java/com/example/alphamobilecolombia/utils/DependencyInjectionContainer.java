package com.example.alphamobilecolombia.utils;

import android.content.Context;

import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.data.remote.instance.implement.MapRequest;
import com.example.alphamobilecolombia.data.remote.instance.implement.RetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ILoginAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.LoginAdapter;
import com.example.alphamobilecolombia.mvp.presenter.ILoginPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.LoginPresenter;
import com.example.alphamobilecolombia.utils.cryptography.implement.MD5Hashing;
import com.example.alphamobilecolombia.utils.cryptography.providers.IMD5Hashing;

public class DependencyInjectionContainer {
    public ILoginPresenter injectDIILoginPresenter (Context context){
        return new LoginPresenter(context,injectDIILoginAdapter(context),injectIMD5Hashing());
    }

    private ILoginAdapter injectDIILoginAdapter(Context context){
    return new LoginAdapter(injectIRetrofitInstance(),injectIMapRequest(),context);
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
}
