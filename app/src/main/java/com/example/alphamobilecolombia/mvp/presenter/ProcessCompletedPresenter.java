package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostSujetoInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostSujetoCredito;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostPersonaInsertar;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProcessCompletedPresenter {


    public HttpResponse PostInsertPerson(Persona person, String user, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_generic),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            PostPersonaInsertar postService = retrofit.create(PostPersonaInsertar.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostPersonaInsertarRequest(person.getCedula(), person.getNombre(), person.getNombre2(), person.getApellido1(), person.getApellido2(), person.getGenero(), person.getFechaNacimiento(), person.getCelular(), user));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Insertar(body1);

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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),person.getCedula(),ex,context);
        }
        return null;
    }

    public HttpResponse PostInsertCreditSubject(Persona persona, String codeTransaction, int typeEmployee, int typeContract, int creditDestination, String registryUser, int codePayMaster, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_generic),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            PostSujetoCredito postService = retrofit.create(PostSujetoCredito.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostSujetoInsertarRequest(codeTransaction, typeEmployee, typeContract, creditDestination,  registryUser, codePayMaster));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Insertar(body1);
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),persona.getCedula(),ex,context);
        }
        return null;
    }
}
