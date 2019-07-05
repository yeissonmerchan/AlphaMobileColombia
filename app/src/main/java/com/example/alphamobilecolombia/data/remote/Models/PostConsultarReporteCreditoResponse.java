package com.example.alphamobilecolombia.data.remote.Models;

import io.realm.RealmObject;

public class PostConsultarReporteCreditoResponse extends RealmObject {
    private String estadoGeneral;
    private String regional;
    private String oficina;
    private String coordinador;
    private String asesor;
    private String pagaduria;
    private String documentoCliente;
    private String cliente;
    private String fechaEnvioPrevalidacion;
    private String montoSugerido;
    private String cuotaSug;
    private String plazoSugerido;
    private String fechaPrevalidacion;
    private String observacionCredito;
    private String numeroSolicitud;
    private String tipoCr;

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getPagaduria() {
        return pagaduria;
    }

    public void setPagaduria(String pagaduria) {
        this.pagaduria = pagaduria;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaEnvioPrevalidacion() {
        return fechaEnvioPrevalidacion;
    }

    public void setFechaEnvioPrevalidacion(String fechaEnvioPrevalidacion) {
        this.fechaEnvioPrevalidacion = fechaEnvioPrevalidacion;
    }

    public String getMontoSugerido() {
        return montoSugerido;
    }

    public void setMontoSugerido(String montoSugerido) {
        this.montoSugerido = montoSugerido;
    }

    public String getCuotaSug() {
        return cuotaSug;
    }

    public void setCuotaSug(String cuotaSug) {
        this.cuotaSug = cuotaSug;
    }

    public String getPlazoSugerido() {
        return plazoSugerido;
    }

    public void setPlazoSugerido(String plazoSugerido) {
        this.plazoSugerido = plazoSugerido;
    }

    public String getFechaPrevalidacion() {
        return fechaPrevalidacion;
    }

    public void setFechaPrevalidacion(String fechaPrevalidacion) {
        this.fechaPrevalidacion = fechaPrevalidacion;
    }

    public String getObservacionCredito() {
        return observacionCredito;
    }

    public void setObservacionCredito(String observacionCredito) {
        this.observacionCredito = observacionCredito;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getTipoCr() {
        return tipoCr;
    }

    public void setTipoCr(String tipoCr) {
        this.tipoCr = tipoCr;
    }

}
