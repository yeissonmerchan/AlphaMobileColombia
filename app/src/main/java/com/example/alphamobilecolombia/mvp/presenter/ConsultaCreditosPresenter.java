package com.example.alphamobilecolombia.mvp.presenter;

import android.os.StrictMode;

import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.PostAutenticationRequest;
import com.example.alphamobilecolombia.data.remote.PostSolicitudes;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConsultaCreditosPresenter {
    public HttpResponse PostConsultarSolicitudes(String idUsuario) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//181.57.145.20:8082
            PostSolicitudes postService = retrofit.create(PostSolicitudes.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostAutenticationRequest("10031460","6d91327bca8251614ee4b0400e3edb67"));
            //String data = gson.toJson(new PostAutenticationRequest(txtUsuario,MD5Hashing.MD5(txtPass)));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.GetListSolicitudes( body1 );//True:False?0101
            //String responseS = call.execute().toString();//.enqueue(callback);

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
