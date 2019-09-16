package com.example.alphamobilecolombia.data.remote.Models.Request;

public class PostPersonaInsertarRequest {
    final String iD_TS_TipoDocumento;
    final String documento;
    final String primerNombre;
    final String segundoNombre;
    final String primerApellido;
    final String segundoApellido;
    final String fechaNacimiento;
    final String iD_TP_MunicipioNacimiento;
    final String iD_TS_Genero;
    final String iD_TS_EstadoCivil;
    final String iD_TP_MunicipioResidencia;
    final String direccion;
    final String telefono;
    final String celular;
    final String fechaExpedicion;
    final String iD_TP_MunicipioExpedicion;
    final String iD_TP_UsuarioRegistro;

    public PostPersonaInsertarRequest(String cedula, String pNombre, String sNombre, String pApellido, String sApellido, String genero, String fechaNacimiento, String celular, String fechaExpedicion, String ciudad, String usuarioRegistro)
    {

        this.iD_TS_TipoDocumento = "1";
        this.documento = cedula;
        this.primerNombre = pNombre;
        this.segundoNombre = sNombre;
        this.primerApellido = pApellido;
        this.segundoApellido = sApellido;
        this.iD_TS_Genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.iD_TP_MunicipioNacimiento = "918";
        this.iD_TS_EstadoCivil = "1";
        this.iD_TP_MunicipioResidencia = "918";
        this.direccion = "";
        this.telefono = "";
        this.celular = celular;
        this.fechaExpedicion = fechaExpedicion;
        //this.fechaExpedicion = "";
        this.iD_TP_MunicipioExpedicion = ciudad;
        this.iD_TP_UsuarioRegistro = usuarioRegistro;

    }


}
