package com.example.alphamobilecolombia.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetVersion {
    String API_ROUTE = "/api/VersionControl/ValidateVersion?";

    @GET(API_ROUTE)
    Call<String> IsValidVersion(@Query("versionApp") String versionId);

}
