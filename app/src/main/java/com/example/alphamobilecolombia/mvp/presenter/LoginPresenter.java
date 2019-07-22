package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.GetVersion;
import com.example.alphamobilecolombia.data.remote.Models.PostAutenticationRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.PostAutentication;

import org.json.JSONObject;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.example.alphamobilecolombia.utils.cryptography.providers.MD5Hashing;
import com.google.gson.Gson;

public class LoginPresenter {

    public HttpResponse PostLogin(String txtUsuario, String txtPass, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        Response response;
        try {
            //TODO: Quitar el policy y poner asíncrono
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                //TODO: Cambiar a implementación de flavors
                String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_autentication),context);//Obtener Ip a partir de configuración
                Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
                //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
                //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//Produccion
                PostAutentication postService = retrofit.create(PostAutentication.class);

                Gson gson = new Gson();
                //String data = gson.toJson(new PostAutenticationRequest("1110449867","188ce4435b977cef83884c051a277cb9"));
                String data = gson.toJson(new PostAutenticationRequest("Desarrollo","6d91327bca8251614ee4b0400e3edb67"));
                //String data = gson.toJson(new PostAutenticationRequest(txtUsuario, MD5Hashing.MD5(txtPass)));
                RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

                Call<String> call = postService.Login(body1);//True:False?0101
                //String responseS = call.execute().toString();//.enqueue(callback);
                JSONObject jsonObject;
                response = call.execute();
                if (!(response.code() != 200)) {
                    jsonObject = new JSONObject(response.body().toString());
                    String value = jsonObject.toString();
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
            }
        return null;
    }


    public HttpResponse GetVersion(String idversion, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        Response response;
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_autentication),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//Producción
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            GetVersion postService = retrofit.create(GetVersion.class);

            Call<String> call = postService.IsValidVersion(idversion);//True:False?0101
            //String responseS = call.execute().toString();//.enqueue(callback);

            response = call.execute();

            JSONObject jsonObject = new JSONObject(response.body().toString());
            String value = jsonObject.toString();
            responseModel.setCode("200");
            responseModel.setData(jsonObject);
            responseModel.setMessage("Solicitud de acceso correcta.");

            return responseModel;

        }
        catch (Exception ex){
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
            responseModel.setCode("409");
            responseModel.setData("");
            responseModel.setMessage("Hemos detectado una versión más reciente de la aplicación, por favor debes actualizarla.");
            return responseModel;
        }
    }
}
