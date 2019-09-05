package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetPaying {
    String API_ROUTE = "/Generic/api/TP_Pagaduria/ConsultarPagaduriasActivas";



    @GET(API_ROUTE)
    Call<String> GetList(
    );

}
