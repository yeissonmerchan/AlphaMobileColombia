package com.example.alphamobilecolombia.mvp.presenter;

import android.os.StrictMode;

import com.example.alphamobilecolombia.data.local.PostAutenticationRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.PostAutentication;
import com.example.alphamobilecolombia.utils.cryptography.providers.MD5Hashing;

import org.json.JSONObject;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import com.google.gson.Gson;

public class LoginPresenter {

    public HttpResponse PostLogin(String txtUsuario, String txtPass) {
        final HttpResponse responseModel = new HttpResponse();
        try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.22:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//181.57.145.20:6235
                PostAutentication postService = retrofit.create(PostAutentication.class);

                Gson gson = new Gson();
                String data = gson.toJson(new PostAutenticationRequest("Desarrollo","6d91327bca8251614ee4b0400e3edb67"));
                //String data = gson.toJson(new PostAutenticationRequest(txtUsuario,MD5Hashing.MD5(txtPass)));
                RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

                Call<String> call = postService.Login( body1 );//True:False?0101
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
