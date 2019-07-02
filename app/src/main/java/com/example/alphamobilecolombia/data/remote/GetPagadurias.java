package com.example.alphamobilecolombia.data.remote;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetPagadurias {
    String API_ROUTE = "/api/TP_Pagaduria/ConsultarPagaduriasActivas";



    @GET(API_ROUTE)
    Call<String> GetList(
    );

}
