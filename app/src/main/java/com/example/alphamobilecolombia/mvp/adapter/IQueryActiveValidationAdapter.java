package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import retrofit2.Call;

public interface IQueryActiveValidationAdapter {
    ApiResponse Get(String documentNumber);
    Call<String> GetAsync(String documentNumber);
}
