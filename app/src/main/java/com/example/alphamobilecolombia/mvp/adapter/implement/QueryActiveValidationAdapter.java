package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetPrevalidacionActiva;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetVersion;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.IQueryActiveValidationAdapter;

import retrofit2.Call;

public class QueryActiveValidationAdapter implements IQueryActiveValidationAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public QueryActiveValidationAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Get(String documentNumber){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        GetPrevalidacionActiva service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(GetPrevalidacionActiva.class);

        Call<String> call = service.GetList(documentNumber);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> GetAsync(String documentNumber){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración

        GetPrevalidacionActiva service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(GetPrevalidacionActiva.class);
        Call<String> call = service.GetList(documentNumber);
        return call;
    }
}
