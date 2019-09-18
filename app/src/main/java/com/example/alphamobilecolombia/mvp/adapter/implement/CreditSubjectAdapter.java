package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostPersonaInsertar;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostSujetoCredito;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostSujetoInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.security.IAccessToken;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class CreditSubjectAdapter implements ICreditSubjectAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public CreditSubjectAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Post(Persona persona, String codeTransaction, int typeEmployee, int typeContract, int creditDestination, String registryUser, int codePayMaster,String fechaIngreso){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_generic),_context);//Obtener Ip a partir de configuración
        PostSujetoCredito service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostSujetoCredito.class);

        Gson gson = new Gson();
        String data = gson.toJson(new PostSujetoInsertarRequest(codeTransaction, typeEmployee, typeContract, creditDestination,  registryUser, codePayMaster, fechaIngreso));
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        Call<String> call = service.Insertar(body);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> PostAsync(Persona persona, String codeTransaction, int typeEmployee, int typeContract, int creditDestination, String registryUser, int codePayMaster,String fechaIngreso){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_generic),_context);//Obtener Ip a partir de configuración
        Gson gson = new Gson();
        String data = gson.toJson(new PostSujetoInsertarRequest(codeTransaction, typeEmployee, typeContract, creditDestination,  registryUser, codePayMaster, fechaIngreso));
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        PostSujetoCredito service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostSujetoCredito.class);
        Call<String> call = service.Insertar(body);
        return call;
    }
}
