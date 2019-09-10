package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GetBusinessLists {
    String API_TCONTRATO_ROUTE = "/Generic/api/TP_TipoContrato/ConsultarTipoContratoPorIdTipoEmpleado/{id}";

    @GET(API_TCONTRATO_ROUTE)
    Call<String> Login(@Path("id")String idTipoEmpleado);

}
