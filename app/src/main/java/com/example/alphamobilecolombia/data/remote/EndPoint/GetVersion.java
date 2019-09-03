package com.example.alphamobilecolombia.data.remote.EndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetVersion {
    String API_ROUTE = "/Security/api/VersionControl/ValidateVersion/{versionApp}";

    @GET(API_ROUTE)
    Call<String> IsValidVersion(@Path("versionApp") String versionId);

}
