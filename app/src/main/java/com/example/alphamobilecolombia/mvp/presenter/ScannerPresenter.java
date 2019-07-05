package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.GetPagadurias;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.PostAutenticationRequest;
import com.example.alphamobilecolombia.data.remote.PostAutentication;
import com.example.alphamobilecolombia.utils.cryptography.providers.MD5Hashing;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ScannerPresenter {

    public HttpResponse getPagadurias(Context context) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_generic),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//Producción
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            GetPagadurias getPagadurias = retrofit.create(GetPagadurias.class);

            /*Gson gson = new Gson();
            String data = gson.toJson(new PostAutenticationRequest(txtUsuario, MD5Hashing.MD5(txtPass)));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);*/

            Call<String> call = getPagadurias.GetList();

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
