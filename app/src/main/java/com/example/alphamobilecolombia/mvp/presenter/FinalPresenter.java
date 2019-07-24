package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.PostSujetoInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.PostSujetoCredito;
import com.example.alphamobilecolombia.data.remote.PostPersonaInsertar;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.models.Persona;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FinalPresenter {


    public HttpResponse PostInsertPerson(Persona persona, String user, Context context) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_generic),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//Producción
            PostPersonaInsertar postService = retrofit.create(PostPersonaInsertar.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostPersonaInsertarRequest(persona.getCedula(), persona.getNombre(), persona.getNombre2(), persona.getApellido1(), persona.getApellido2(), persona.getGenero(), persona.getFechaNacimiento(), persona.getCelular(), user));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Insertar(body1);

            Response response = call.execute();

            JSONObject jsonObject;
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),persona.getCedula(),ex,context);
        }
        return null;
    }

    public HttpResponse PostInsertSujetoCredito(Persona persona, String codigoTransaccion, int iD_ts_tipoEmpleado, int iD_tp_tipoContrato, int iD_ts_destinoCredito, String iD_TP_UsuarioRegistro, int idPagaduria, Context context) {
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
            PostSujetoCredito postService = retrofit.create(PostSujetoCredito.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostSujetoInsertarRequest(codigoTransaccion, iD_ts_tipoEmpleado, iD_tp_tipoContrato, iD_ts_destinoCredito,  iD_TP_UsuarioRegistro, idPagaduria));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Insertar( body1 );

            Response response = call.execute();

            JSONObject jsonObject;
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),persona.getCedula(),ex,context);
        }
        return null;
    }
}
