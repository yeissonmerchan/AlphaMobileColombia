package com.example.alphamobilecolombia.data.remote.Models;

public class PostConsultarReporteCreditoRequest {
    final String pTipo;
    final String pValor;
    final String pIdRol;
    final String pIdUsuario;
    final String pFechaInicio;
    final String pFechaFin;

    public PostConsultarReporteCreditoRequest(String pTipo, String pValor, String pIdRol, String pIdUsuario, String pFechaInicio, String pFechaFin)
    {
        this.pTipo = pTipo;
        this.pValor = pValor;
        this.pIdRol = pIdRol;
        this.pIdUsuario = pIdUsuario;
        this.pFechaInicio = pFechaInicio;
        this.pFechaFin = pFechaFin;
    }
}
