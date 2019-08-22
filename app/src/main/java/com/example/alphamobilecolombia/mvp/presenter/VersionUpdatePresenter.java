package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetVersion;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class VersionUpdatePresenter {

    public HttpResponse GetVersion(String idVersion, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        Response response;
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            //String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_authentication),context);//Obtener Ip a partir de configuración
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_authentication),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            GetVersion postService = retrofit.create(GetVersion.class);

            Call<String> call = postService.IsValidVersion(idVersion);
            JSONObject jsonObject;
            response = call.execute();
            if (!(response.code() != 200)) {
                jsonObject = new JSONObject(response.body().toString());
                responseModel.setCode(String.valueOf(response.code()));
                responseModel.setData(jsonObject);
                responseModel.setMessage(response.message());
            }
            else{
                String errorResponse = response.errorBody().string();
                JSONObject object = new JSONObject(errorResponse);
                responseModel.setCode(String.valueOf(response.code()));
                responseModel.setData(object);
                responseModel.setMessage(String.valueOf(object.get("mensaje")));
            }

            return responseModel;

        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),idVersion,ex,context);
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
            responseModel.setCode("409");
            responseModel.setData("");
            responseModel.setMessage("Hemos detectado una versión más reciente de la aplicación, por favor debes actualizarla.");
            return responseModel;
        }
    }
}
