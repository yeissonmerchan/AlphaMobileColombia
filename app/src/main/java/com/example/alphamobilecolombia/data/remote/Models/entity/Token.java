package com.example.alphamobilecolombia.data.remote.Models.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;

public class Token extends RealmObject {

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

    public String getTokenRenovation() {
        return TokenRenovation;
    }

    public void setTokenRenovation(String tokenRenovation) {
        TokenRenovation = tokenRenovation;
    }

    @SerializedName("tokenRenovacion")
    private String TokenRenovation;
}
