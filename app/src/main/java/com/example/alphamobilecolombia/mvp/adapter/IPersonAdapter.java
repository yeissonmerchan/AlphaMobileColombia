package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.models.Persona;

import retrofit2.Call;

public interface IPersonAdapter {
    ApiResponse Post(Persona person, String idUser);
    Call<String> PostAsync(Persona person, String idUser);
}
