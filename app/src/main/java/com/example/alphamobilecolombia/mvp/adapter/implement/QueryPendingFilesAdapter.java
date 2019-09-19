package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetIsValidListFiles;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetPendingFiles;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.IQueryPendingFilesAdapter;

import retrofit2.Call;

public class QueryPendingFilesAdapter implements IQueryPendingFilesAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public QueryPendingFilesAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Get(String idSubjectCredit){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_storage),_context);//Obtener Ip a partir de configuración
        GetPendingFiles service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(GetPendingFiles.class);

        Call<String> call = service.IsValidCredit(idSubjectCredit);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> GetAsync(String idSubjectCredit){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración

        GetPendingFiles service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(GetPendingFiles.class);
        Call<String> call = service.IsValidCredit(idSubjectCredit);
        return call;
    }
}
