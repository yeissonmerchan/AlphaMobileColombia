package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetPaying;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostRefreshToken;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.entity.Token;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.IProxyServiceAdapter;

import retrofit2.Call;

public class ProxyServiceAdapter implements IProxyServiceAdapter {
    IRealmInstance _iRealmInstance;
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;
    public ProxyServiceAdapter(IRealmInstance iRealmInstance, IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRealmInstance = iRealmInstance;
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Post(Token token){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        PostRefreshToken service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostRefreshToken.class);

        Call<String> call = service.GetRefreshToken(token.getJwt(),token.getTokenRenovation());
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> PostAsync(Token token){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración

        PostRefreshToken service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostRefreshToken.class);
        Call<String> call = service.GetRefreshToken(token.getJwt(),token.getTokenRenovation());
        return call;
    }
}
