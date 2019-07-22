package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.GetPrevalidacionActiva;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConsultarPrevalidacionActivaPresenter {
    public HttpResponse GetConsultarPrevalidacionActiva(String documento, Context context){
        final HttpResponse responseModel = new HttpResponse();
        try {


            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_generic),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            GetPrevalidacionActiva postService = retrofit.create(GetPrevalidacionActiva.class);

            Call<String> call = postService.GetList(documento);

            Response response = call.execute();

            JSONObject jsonObject = new JSONObject(response.body().toString());
            String value = jsonObject.toString();
            responseModel.setCode("200");
            responseModel.setData(jsonObject);
            responseModel.setMessage("Solicitud de acceso correcta.");

            return responseModel;

        }
        catch (Exception ex){
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
        }
        return null;
    }
}
