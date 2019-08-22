package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.models.Persona;

import retrofit2.Call;

public interface ICreditSubjectAdapter {
    ApiResponse Post(Persona persona, String codeTransaction, int typeEmployee, int typeContract, int creditDestination, String registryUser, int codePayMaster);
    Call<String> PostAsync(Persona persona, String codeTransaction, int typeEmployee, int typeContract, int creditDestination, String registryUser, int codePayMaster);
}
