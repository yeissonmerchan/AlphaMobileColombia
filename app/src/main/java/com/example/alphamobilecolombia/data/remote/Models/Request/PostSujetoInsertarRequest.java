package com.example.alphamobilecolombia.data.remote.Models.Request;

import java.util.Date;

public class PostSujetoInsertarRequest {
    final String iD_TP_Persona;
    final int iD_TS_Pagaduria;
    final String iD_TP_Sucursal;
    final String iD_TS_Regional;
    final String iD_TP_Asesor;
    final String iD_TP_Coordinador;
    final String fechaIngreso;
    final String tiempoServicioMeses;
    final String tiempoServicioAnos;
    final String codigoEmpleado;
    final int iD_TS_TipoEmpleado;
    final int iD_TP_TipoContrato;
    final String iD_TS_TipoVivienda;
    final String salario;
    final String personasACargo;
    final String iD_TS_TipoCredito;
    final int iD_TS_DestinoCredito;
    final String montoSolicitado;
    final String plazoSolicitado;
    final String corretajeComercial;
    final String puntajeDataCredito;
    final String tasaAprobada;
    final String observacionOperativa;
    final String observacionEstudio;
    final String iD_TS_EstadoSujetoCredito;
    final String fechaValidacion;
    final String iD_TP_Analista;
    final String fechaEnvio;
    final String montoSugerido;
    final String cuotaSugerida;
    final String plazoSugerido;
    final boolean whatsApp;
    final String fechaRegistro;
    final String iD_TP_UsuarioRegistro;
    final String fechaResolucion;
    final String codigoMesada;

    public PostSujetoInsertarRequest(String iD_tp_persona, int iD_ts_tipoEmpleado, int iD_tp_tipoContrato, int iD_ts_destinoCredito, String iD_TP_UsuarioRegistro, int idPagaduria, String fechaIngreso) {
        iD_TP_Persona = iD_tp_persona;
        iD_TS_Pagaduria = idPagaduria;
        iD_TP_Sucursal = "1";
        iD_TS_Regional = "1";
        iD_TP_Asesor = "1";
        iD_TP_Coordinador = "1";
        this.fechaIngreso = fechaIngreso;
        this.tiempoServicioMeses = "1";
        this.tiempoServicioAnos = "1";
        this.codigoEmpleado = "";
        iD_TS_TipoEmpleado = iD_ts_tipoEmpleado;
        iD_TP_TipoContrato = iD_tp_tipoContrato;
        iD_TS_TipoVivienda = "4";
        this.salario = "0";
        this.personasACargo = "0";
        iD_TS_TipoCredito = "1";
        iD_TS_DestinoCredito = iD_ts_destinoCredito;
        this.montoSolicitado = "0";
        this.plazoSolicitado = "0";//Plazo
        this.corretajeComercial = "0"; //Pago a Terceros
        this.puntajeDataCredito = "0";
        this.tasaAprobada = "0";
        this.observacionOperativa = "";
        this.observacionEstudio = "";
        iD_TS_EstadoSujetoCredito = "8";
        this.fechaValidacion = "1999/01/01";
        iD_TP_Analista = "1";
        this.fechaEnvio = "1999/01/01";
        this.montoSugerido = "0";
        this.cuotaSugerida = "0";//cuota
        this.plazoSugerido = "0";//Plazo
        this.whatsApp = false;
        this.fechaRegistro = "1999/01/01";
        this.fechaResolucion = "1999/01/01";
        this.codigoMesada = "";
        this.iD_TP_UsuarioRegistro = iD_TP_UsuarioRegistro;

    }
}
