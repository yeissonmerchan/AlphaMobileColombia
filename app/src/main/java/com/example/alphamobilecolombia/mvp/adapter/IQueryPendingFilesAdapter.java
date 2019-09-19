package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import retrofit2.Call;

public interface IQueryPendingFilesAdapter {
    ApiResponse Get(String idSubjectCredit);
    Call<String> GetAsync(String idSubjectCredit);
}
