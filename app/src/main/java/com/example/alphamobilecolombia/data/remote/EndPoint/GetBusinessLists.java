package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;


public interface GetBusinessLists {
    String API_TCONTRATO_ROUTE = "/api/TP_TipoContrato/ConsultarTipoContratoPorIdTipoEmpleado";

    @GET(API_TCONTRATO_ROUTE)
    Call<String> Login(
            String idTipoEmpleado
    );

}
