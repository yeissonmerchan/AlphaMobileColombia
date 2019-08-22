package com.example.alphamobilecolombia.data.remote.EndPoint;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostAutentication {
    String API_ROUTE = "/api/Autenticacion/Login";

    @POST(API_ROUTE)
    Call<String> Login(
            @Body RequestBody body
    );
}
