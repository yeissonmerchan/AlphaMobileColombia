package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetIsValidListFiles;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetVersion;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ICompletedSubjectCredit;

import retrofit2.Call;

public class CompletedSubjectCreditAdapter implements ICompletedSubjectCredit {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public CompletedSubjectCreditAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Get(String idSubjectCredit){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_generic),_context);//Obtener Ip a partir de configuración
        GetIsValidListFiles service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(GetIsValidListFiles.class);

        Call<String> call = service.IsValidCredit(idSubjectCredit);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> GetAsync(String idSubjectCredit){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración

        GetIsValidListFiles service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(GetIsValidListFiles.class);
        Call<String> call = service.IsValidCredit(idSubjectCredit);
        return call;
    }
}
