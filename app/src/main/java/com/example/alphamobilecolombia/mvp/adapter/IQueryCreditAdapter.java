package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import retrofit2.Call;

public interface IQueryCreditAdapter {
    ApiResponse Post(String typeQuery, String pValor, String pIdRol, String idUser, String initDate, String endDate);

    Call<String> PostAsync(String idUser);
}
