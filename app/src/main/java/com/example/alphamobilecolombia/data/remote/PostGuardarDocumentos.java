package com.example.alphamobilecolombia.data.remote;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostGuardarDocumentos {
    String API_ROUTE = "/api/TPArchivo/GuardarDocumentos";

    @POST(API_ROUTE)
    Call<String> Upload(
            @Body RequestBody body

    );
}
