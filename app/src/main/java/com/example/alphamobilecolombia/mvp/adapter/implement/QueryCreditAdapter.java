package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostSolicitudes;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostConsultarReporteCreditoRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.IQueryCreditAdapter;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class QueryCreditAdapter implements IQueryCreditAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public QueryCreditAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Post(String idUser){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        PostSolicitudes service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostSolicitudes.class);

        Gson gson = new Gson();
        String data = gson.toJson(new PostConsultarReporteCreditoRequest("6","3","18",idUser,"2019-07-02","2019-07-02"));
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        Call<String> call = service.GetListSolicitudes(body);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> PostAsync(String idUser){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        Gson gson = new Gson();
        String data = gson.toJson(new PostConsultarReporteCreditoRequest("6","3","18",idUser,"2019-07-02","2019-07-02"));
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        PostSolicitudes service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostSolicitudes.class);
        Call<String> call = service.GetListSolicitudes(body);
        return call;
    }
}
