package com.example.alphamobilecolombia.mvp.presenter;

import android.os.StrictMode;

import com.example.alphamobilecolombia.data.remote.Models.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.PostSujetoInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.PostSujetoCredito;
import com.example.alphamobilecolombia.data.remote.PostPersonaInsertar;
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


    public HttpResponse PostInsertPerson(Persona persona, String user) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//181.57.145.20:6235
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//181.57.145.20:6235
            PostPersonaInsertar postService = retrofit.create(PostPersonaInsertar.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostPersonaInsertarRequest(persona.getCedula(), persona.getNombre(), persona.getApellido1(), persona.getApellido2(), persona.getGeneroId(), persona.getFechaNacimiento(), persona.getCelular(), user));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Insertar( body1 );

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

    public HttpResponse PostInsertSujetoCredito(Persona persona, String codigoTransaccion, int iD_ts_tipoEmpleado, int iD_tp_tipoContrato, int iD_ts_destinoCredito, String iD_TP_UsuarioRegistro, int idPagaduria) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8082/").addConverterFactory(ScalarsConverterFactory.create()).build();//181.57.145.20:6235
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8081/").addConverterFactory(ScalarsConverterFactory.create()).build();//181.57.145.20:6235
            PostSujetoCredito postService = retrofit.create(PostSujetoCredito.class);

            Gson gson = new Gson();
            String data = gson.toJson(new PostSujetoInsertarRequest(codigoTransaccion, iD_ts_tipoEmpleado, iD_tp_tipoContrato, iD_ts_destinoCredito,  iD_TP_UsuarioRegistro, idPagaduria));
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Insertar( body1 );

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
