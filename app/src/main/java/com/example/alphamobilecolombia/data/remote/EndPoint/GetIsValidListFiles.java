package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetIsValidListFiles {
    String API_ROUTE = "/Generic/api/TM_SujetoCredito/FinalizaProceso{sujetoCredito}";

    @POST(API_ROUTE)
    Call<String> IsValidCredit(@Path("sujetoCredito") String idSujetoCredito);

}
