package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import retrofit2.Call;

public interface ILoginAdapter {
    ApiResponse Post(String txtUser, String txtPassword);
    Call<String> PostAsync(String txtUser, String txtPassword);
}
