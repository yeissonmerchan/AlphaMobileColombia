package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostGuardarDocumentos;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostPersonaInsertar;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostSaveDocumentRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.example.alphamobilecolombia.utils.files.implement.FileStorage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

public class UploadFileAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;
    String _pathFile;
    FileStorage _fileStorage;

    public UploadFileAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context, String pathFile){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
        _pathFile = pathFile;
    }

    public ApiResponse Post(PostSaveDocumentRequest postSaveDocumentRequest){
        List<PostSaveDocumentRequest> listDocuments = new ArrayList<>();
        listDocuments.add(postSaveDocumentRequest);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        PostGuardarDocumentos service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostGuardarDocumentos.class);

        Gson gson = new Gson();
        String data = gson.toJson(listDocuments);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);

        Call<String> call = service.Upload(body);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> PostAsync(PostSaveDocumentRequest postSaveDocumentRequest){
        List<PostSaveDocumentRequest> listDocuments = new ArrayList<>();
        listDocuments.add(postSaveDocumentRequest);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_authentication),_context);//Obtener Ip a partir de configuración
        Gson gson = new Gson();
        String data = gson.toJson(listDocuments);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);

        PostGuardarDocumentos service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostGuardarDocumentos.class);
        Call<String> call = service.Upload(body);
        return call;
    }
}
