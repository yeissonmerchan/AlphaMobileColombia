package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import retrofit2.Call;

public interface IVersionUpdateAdapter {
    ApiResponse Get(String idVersion);
    Call<String> GetAsync(String idVersion);
}
