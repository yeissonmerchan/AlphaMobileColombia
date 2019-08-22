package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPrevalidacionActiva {
    String API_ROUTE = "/api/ConsultarPrevalidacionActiva/ConsultarPrevalidacionActiva";

    @GET(API_ROUTE)
    Call<String> GetList(
            @Query("Documento") String val
    );
}
