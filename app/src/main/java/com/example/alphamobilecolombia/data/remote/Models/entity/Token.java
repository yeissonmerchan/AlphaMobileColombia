package com.example.alphamobilecolombia.data.remote.Models.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Token {

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getJwt() {
        return Jwt;
    }

    public void setJwt(String jwt) {
        Jwt = jwt;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    @SerializedName("tipoToken")
    private String Type;
    @SerializedName("tokenAcceso")
    private String Jwt;
    @SerializedName("TiempoExpiracion")
    private String ExpirationDate;
    //httpResponse = new Gson().fromJson(response.body().toString(), ApiResponse.class);
}
