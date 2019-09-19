package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetPendingFiles {
    String API_ROUTE = "/Storage/api/TPArchivo/ConsultaListaArchivosPendientes/{idSujetoCredito}";

    @GET(API_ROUTE)
    Call<String> IsValidCredit(@Path("idSujetoCredito") String idSujetoCredito);
}
