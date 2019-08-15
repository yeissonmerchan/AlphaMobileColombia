package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.PostAutenticationRequest;
import com.example.alphamobilecolombia.data.remote.PostAutentication;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.cryptography.providers.MD5Hashing;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginPresenter {

    public HttpResponse Post(String txtUser, String txtPassword, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        Response response;
        try {
                //TODO: Quitar el policy y poner asíncrono
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                //TODO: Cambiar a implementación de flavors
                String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_authentication),context);//Obtener Ip a partir de configuración
                Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
                PostAutentication postService = retrofit.create(PostAutentication.class);

                Gson gson = new Gson();
                String currentlyEnvironment = context.getResources().getString(R.string.environment_development);
                String data;
                if(currentlyEnvironment.contains("DEV")) {
                    data = gson.toJson(new PostAutenticationRequest("Desarrollo", "6d91327bca8251614ee4b0400e3edb67"));
                }
                else{
                    data = gson.toJson(new PostAutenticationRequest(txtUser, MD5Hashing.MD5(txtPassword)));
                }
                RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

                Call<String> call = postService.Login(body1);
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
                System.out.println("Ha ocurrido un error! "+ex.getMessage());
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),txtUser,ex,context);
            }
        return null;
    }

}
