package com.example.alphamobilecolombia.mvp.adapter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostPersonaInsertar;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostSolicitudes;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostConsultarReporteCreditoRequest;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.IPersonAdapter;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class PersonAdapter implements IPersonAdapter {
    IRetrofitInstance _iRetrofitInstance;
    IMapRequest _iMapRequest;
    Context _context;

    public PersonAdapter(IRetrofitInstance iRetrofitInstance, IMapRequest iMapRequest, Context context){
        _iRetrofitInstance = iRetrofitInstance;
        _iMapRequest = iMapRequest;
        _context = context;
    }

    public ApiResponse Post(Persona person, String idUser){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_generic),_context);//Obtener Ip a partir de configuración
        PostPersonaInsertar service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostPersonaInsertar.class);

        Gson gson = new Gson();
        String data = gson.toJson(new PostPersonaInsertarRequest(person.getCedula(), person.getNombre(), person.getNombre2(), person.getApellido1(), person.getApellido2(), person.getGenero(), person.getFechaNacimiento(), person.getCelular(), idUser));
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        Call<String> call = service.Insertar(body);
        return _iMapRequest.SynchronousRequest(call);
    }

    public Call<String> PostAsync(Persona person, String idUser){
        String urlApi = ApiEnviroment.GetIpAddressApi(_context.getResources().getString(R.string.api_generic),_context);//Obtener Ip a partir de configuración
        Gson gson = new Gson();
        String data = gson.toJson(new PostPersonaInsertarRequest(person.getCedula(), person.getNombre(), person.getNombre2(), person.getApellido1(), person.getApellido2(), person.getGenero(), person.getFechaNacimiento(), person.getCelular(), idUser));
        RequestBody body = RequestBody.create( MediaType.parse("application/json"), data);

        PostPersonaInsertar service = _iRetrofitInstance.getRetrofitInstance(urlApi).create(PostPersonaInsertar.class);
        Call<String> call = service.Insertar(body);
        return call;
    }
}
