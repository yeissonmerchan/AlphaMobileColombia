package com.example.alphamobilecolombia.data.remote.EndPoint;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostGuardarListaTotalArchivos {
    String API_ROUTE = "/api/TPArchivo/GuardarListaTotalArchivos";

    @POST(API_ROUTE)
    Call<String> Upload(
            @Body RequestBody body

    );
}
