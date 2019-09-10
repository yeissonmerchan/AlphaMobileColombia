package com.example.alphamobilecolombia.data.remote.EndPoint;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostSujetoCredito {
    String API_ROUTE = "/Generic/api/TM_SujetoCredito/Insertar";

    @POST(API_ROUTE)
    Call<String> Insertar(
            @Body RequestBody body
    );
}
