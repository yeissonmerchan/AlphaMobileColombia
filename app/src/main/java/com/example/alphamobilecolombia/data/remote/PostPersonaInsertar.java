package com.example.alphamobilecolombia.data.remote;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostPersonaInsertar {
    String API_ROUTE = "/api/TP_Persona/Insertar";

    @POST(API_ROUTE)
    Call<String> Insertar(
            @Body RequestBody body


    );
}
