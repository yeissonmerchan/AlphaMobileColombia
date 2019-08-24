package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostAutentication;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostAutenticationRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ILoginAdapter;
import com.example.alphamobilecolombia.utils.cryptography.implement.MD5Hashing;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class LoginAdapter implements ILoginAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public LoginAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Post(String txtUser, String txtPassword){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        PostAutentication service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostAutentication.class);

        Gson gson = new Gson();
        String currentlyEnvironment = _context.getResources().getString(R.string.environment_development);
        String data;
        if(currentlyEnvironment.contains("DEV")) {
            data = gson.toJson(new PostAutenticationRequest("Desarrollo", "6d91327bca8251614ee4b0400e3edb67"));
        }
        else{
            data = gson.toJson(new PostAutenticationRequest(txtUser, MD5Hashing.MD5(txtPassword)));
        }
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        Call<String> call = service.Login(body);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> PostAsync(String txtUser, String txtPassword){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        Gson gson = new Gson();
        String currentlyEnvironment = _context.getResources().getString(R.string.environment_development);
        String data;
        if(currentlyEnvironment.contains("DEV")) {
            data = gson.toJson(new PostAutenticationRequest("Desarrollo", "6d91327bca8251614ee4b0400e3edb67"));
        }
        else{
            data = gson.toJson(new PostAutenticationRequest(txtUser, MD5Hashing.MD5(txtPassword)));
        }
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        PostAutentication service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostAutentication.class);
        Call<String> call = service.Login(body);
        return call;
    }
}
