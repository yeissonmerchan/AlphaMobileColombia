package com.example.alphamobilecolombia.data.remote.instance.implement;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.instance.IMapRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import freemarker.core.ReturnInstruction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRequest implements IMapRequest {

    public <T> ApiResponse SynchronousRequest(Call<T> call){
        ApiResponse httpResponse = new ApiResponse();
        try {
            Response response = call.execute();
            if (!(response.code() != 200)) {
                JSONObject jsonObject = new JSONObject(response.body().toString());
                JSONArray jSONArray = (JSONArray) jsonObject.getJSONArray("data");
                httpResponse = new Gson().fromJson(response.body().toString(), ApiResponse.class);
                httpResponse.setData(jSONArray);
            }
            else{
                String errorResponse = response.errorBody().string();
                JSONObject object = new JSONObject(errorResponse);
                httpResponse.setCodigoRespuesta(Integer.parseInt(String.valueOf(response.code())));
                httpResponse.setData(object);
                httpResponse.setMensaje(String.valueOf(object.get("mensaje")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.setCodigoRespuesta(Integer.parseInt("500"));
            httpResponse.setMensaje(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            httpResponse.setCodigoRespuesta(Integer.parseInt("500"));
            httpResponse.setMensaje(e.getMessage());
        }
        return httpResponse;
    }
}
