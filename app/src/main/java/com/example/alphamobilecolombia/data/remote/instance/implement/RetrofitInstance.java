package com.example.alphamobilecolombia.data.remote.instance.implement;

import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInstance implements IRetrofitInstance {

    private Retrofit retrofit;
    /*private static final String BASE_URL = "https://api.myjson.com/";*/

    /**
     * Create an instance of Retrofit object
     * */
    public Retrofit getRetrofitInstance(String urlApi) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(urlApi)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}