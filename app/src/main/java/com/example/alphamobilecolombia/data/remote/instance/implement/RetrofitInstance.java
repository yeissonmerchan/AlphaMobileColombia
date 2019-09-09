package com.example.alphamobilecolombia.data.remote.instance.implement;

import com.example.alphamobilecolombia.data.remote.Models.entity.Token;
import com.example.alphamobilecolombia.data.remote.instance.IRetrofitInstance;
import com.example.alphamobilecolombia.utils.security.IAccessToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInstance implements IRetrofitInstance {

    private Retrofit retrofit;
    IAccessToken _iAccessToken;

    public RetrofitInstance(IAccessToken iAccessToken) {
        _iAccessToken = iAccessToken;
    }

    /*private static final String BASE_URL = "https://api.myjson.com/";*/

    /**
     * Create an instance of Retrofit object
     */
    public Retrofit getRetrofitInstance(String urlApi) {
        try {
            Token token = _iAccessToken.RefreshAccess();
            if (token != null) {
                OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
                okHttpClientBuilder
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Request.Builder newRequest = request.newBuilder().header("Authorization", token.getType() + " " + token.getJwt());
                                return chain.proceed(newRequest.build());
                            }
                        });

                if (retrofit == null) {
                    retrofit = new retrofit2.Retrofit.Builder()
                            .baseUrl(urlApi)
                            .client(okHttpClientBuilder.build())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
                }
            }
            else{
                if (retrofit == null) {
                    retrofit = new retrofit2.Retrofit.Builder()
                            .baseUrl(urlApi)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retrofit;
    }
}