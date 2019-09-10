package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.entity.Token;

import retrofit2.Call;

public interface IProxyServiceAdapter {
    ApiResponse Post(Token token);
    Call<String> PostAsync(Token token);
}
