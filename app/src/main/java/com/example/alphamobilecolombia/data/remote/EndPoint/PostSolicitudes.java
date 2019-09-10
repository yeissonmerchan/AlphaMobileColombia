package com.example.alphamobilecolombia.data.remote.EndPoint;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostSolicitudes
{
        String API_ROUTE = "/Generic/api/ConsultarReporteCredito/GetList";
        //@Header("Authorization", your key);
        @POST(API_ROUTE)
        Call<String> GetListSolicitudes(
                @Body RequestBody body
        );
}
