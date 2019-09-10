package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetPrevalidacionActiva {
    String API_ROUTE = "/Generic/api/ConsultarPrevalidacionActiva/ConsultarPrevalidacionActiva/{Documento}";

    @GET(API_ROUTE)
    Call<String> GetList(
            @Path("Documento") String val
    );
}
