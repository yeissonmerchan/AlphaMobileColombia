package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostRefreshToken {
    String API_ROUTE = "/Security/api/Autenticacion/RefreshToken";

    @POST(API_ROUTE)
    Call<String> GetRefreshToken(@Query("token") String jwt, @Query("refreshToken") String refreshToken);
}
