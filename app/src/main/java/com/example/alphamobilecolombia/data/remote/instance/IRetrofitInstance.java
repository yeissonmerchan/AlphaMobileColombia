package com.example.alphamobilecolombia.data.remote.instance;

import retrofit2.Retrofit;

public interface IRetrofitInstance {

    //Instancia de retrofit
    Retrofit getRetrofitInstance(String urlApi);
}
