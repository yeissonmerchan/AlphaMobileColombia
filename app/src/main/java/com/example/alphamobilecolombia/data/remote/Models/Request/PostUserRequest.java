package com.example.alphamobilecolombia.data.remote.Models.Request;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostUserRequest {
    public String usuario = "";
    public String idUsuario = "";
    public String idRol = "";
    public String nombre = "";



    public void setData(SharedPreferences sharedPref, JSONObject objeto, String usuario) throws JSONException {

        //JSONArray data = objeto.optJSONArray("data");

        this.usuario = usuario;
        this.idUsuario = objeto.getString("idUsuario");
        this.idRol = objeto.getString("idRol");
        this.nombre = objeto.getString("nombreCompleto");

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user", this.usuario);
        editor.putString("idUser", this.idUsuario);
        editor.putString("idRol", this.idRol);
        editor.putString("nombre", this.nombre);
        editor.commit();

    }
    public void getData(SharedPreferences sharedPref) throws JSONException {

        this.usuario = sharedPref.getString("user", "");
        this.idUsuario = sharedPref.getString("idUsuario", "");
        this.idRol = sharedPref.getString("idRol", "");
        this.nombre = sharedPref.getString("nombre", "");

    }
}