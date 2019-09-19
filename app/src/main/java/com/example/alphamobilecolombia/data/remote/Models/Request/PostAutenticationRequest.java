package com.example.alphamobilecolombia.data.remote.Models.Request;

public class PostAutenticationRequest {

    final String usuario;
    final String contrasena;
    final String versionDispositivo;
    final String nombreDispositivo;
    final int idTipoDispositivo;

    public PostAutenticationRequest(String usuario, String contrasena, String versionDispositivo, String nombreDispositivo, int idTipoDispositivo)
    {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.versionDispositivo = versionDispositivo;
        this.nombreDispositivo = nombreDispositivo;
        this.idTipoDispositivo = idTipoDispositivo;
    }
}
