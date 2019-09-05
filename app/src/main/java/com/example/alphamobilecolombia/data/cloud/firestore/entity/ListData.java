package com.example.alphamobilecolombia.data.cloud.firestore.entity;

public class ListData {
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    /// <summary>
    /// Get - Set Nombre
    /// </summary>
    private String Nombre;

    /// <summary>
    /// Get - Set Codigio
    /// </summary>
    private int Codigo;
}
