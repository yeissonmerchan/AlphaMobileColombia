package com.example.alphamobilecolombia.data.remote.Models.Request;

public class PostSaveDocumentRequest {
    private int sujetoCreditoID;
    //private String rutaArchivo;
    private int tipoArchivoID;
    private String tipoArchivoNombre;
    private int usuarioRegistroID;
    private String archivo;
    private String nombreArchivo;
    private String extensionArchivo;

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }

    public int getSujetoCreditoID() {
        return sujetoCreditoID;
    }

    public void setSujetoCreditoID(int sujetoCreditoID) {
        this.sujetoCreditoID = sujetoCreditoID;
    }

    //public String getRutaArchivo() {return rutaArchivo;}

    //public void setRutaArchivo(String rutaArchivo) {this.rutaArchivo = rutaArchivo;}

    public int getTipoArchivoID() {
        return tipoArchivoID;
    }

    public void setTipoArchivoID(int tipoArchivoID) {
        this.tipoArchivoID = tipoArchivoID;
    }

    public String getTipoArchivoNombre() {
        return tipoArchivoNombre;
    }

    public void setTipoArchivoNombre(String tipoArchivoNombre) {
        this.tipoArchivoNombre = tipoArchivoNombre;
    }

    public int getUsuarioRegistroID() {
        return usuarioRegistroID;
    }

    public void setUsuarioRegistroID(int usuarioRegistroID) {
        this.usuarioRegistroID = usuarioRegistroID;
    }


}
