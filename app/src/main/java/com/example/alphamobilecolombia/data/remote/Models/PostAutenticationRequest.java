package com.example.alphamobilecolombia.data.remote.Models;

public class PostAutenticationRequest {

    final String usuario;
    final String contrasena;

    public PostAutenticationRequest(String usuario, String contrasena)
    {
        this.usuario = usuario;
        this.contrasena = contrasena;

    }
}
