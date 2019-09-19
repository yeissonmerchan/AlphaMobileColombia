package com.example.alphamobilecolombia.data.remote.instance;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;

import retrofit2.Call;

public interface IMapRequest {
    <T> ApiResponse SynchronousRequest(Call<T> call);
}
