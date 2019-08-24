package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostConsultarReporteCreditoRequest;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostSolicitudes;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class QueryCreditPresenter {
    public HttpResponse Post(String idUsuario, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_generic),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8082/").addConverterFactory(ScalarsConverterFactory.create()).build()//Producción
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            PostSolicitudes postService = retrofit.create(PostSolicitudes.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostConsultarReporteCreditoRequest("6","3","18",idUsuario,"2019-07-02","2019-07-02"));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.GetListSolicitudes( body1 );//True:False?0101
            //String responseS = call.execute().toString();//.enqueue(callback);

            Response response = call.execute();

            JSONObject jsonObject;
            if (response.code() == 200) {
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
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),idUsuario,ex,context);
        }
        return null;
    }
}
