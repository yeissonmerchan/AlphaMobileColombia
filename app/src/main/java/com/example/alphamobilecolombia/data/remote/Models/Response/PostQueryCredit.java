package com.example.alphamobilecolombia.data.remote.Models.Response;

import com.google.gson.annotations.SerializedName;

public class PostQueryCredit {
    @SerializedName("idSujeto")
    private int IdSubject;

    @SerializedName("idCredito")
    private int IdCredit;

    @SerializedName("estadoGeneral")
    private String GeneralState;
    private String estadoGeneral;
    private String regional;
    private String oficina;
    private String coordinador;
    private String asesor;
    private String pagaduria;
    private int documentoCliente;
    private String cliente;
    private String fechaEnvioPrevalidacion;
    private int montoSugerido;
    private int cuotaSug;
    private int plazoSugerido;
    private String fechaPrevalidacion;
    private String obsPrevalidacion;
    private String estadoPrevalidacion;
    private String observacionCredito;
    private int numeroSolicitud;
    private int numeroLibranza;
    private String fechaRadicacion;
    private String tipoCr;
    private int montoSolicitado;
    private int plazoSolicitado;
    private int montoAprobado;
    private int cuotaAprob;
    private int plazoAprob;
    private String obsPreaprobacion;
    private String fechaAprobacionCredito;
    private String obsAprobacion;
    private String estado;
    private String fabrica;
    private int idAsesor;
    private String fechaDesembolso;
    private int idCoordinador;
    private int idRegional;
    private int idOficina;
    private String estadoLibranza;
    private String observicionLlamadaCliente;


    public int getIdSubject() {
        return IdSubject;
    }

    public void setIdSubject(int idSubject) {
        IdSubject = idSubject;
    }

    public int getIdCredit() {
        return IdCredit;
    }

    public void setIdCredit(int idCredit) {
        IdCredit = idCredit;
    }

    public String getGeneralState() {
        return GeneralState;
    }

    public void setGeneralState(String generalState) {
        GeneralState = generalState;
    }

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

    public int getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(int documentoCliente) {
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

    public int getMontoSugerido() {
        return montoSugerido;
    }

    public void setMontoSugerido(int montoSugerido) {
        this.montoSugerido = montoSugerido;
    }

    public int getCuotaSug() {
        return cuotaSug;
    }

    public void setCuotaSug(int cuotaSug) {
        this.cuotaSug = cuotaSug;
    }

    public int getPlazoSugerido() {
        return plazoSugerido;
    }

    public void setPlazoSugerido(int plazoSugerido) {
        this.plazoSugerido = plazoSugerido;
    }

    public String getFechaPrevalidacion() {
        return fechaPrevalidacion;
    }

    public void setFechaPrevalidacion(String fechaPrevalidacion) {
        this.fechaPrevalidacion = fechaPrevalidacion;
    }

    public String getObsPrevalidacion() {
        return obsPrevalidacion;
    }

    public void setObsPrevalidacion(String obsPrevalidacion) {
        this.obsPrevalidacion = obsPrevalidacion;
    }

    public String getEstadoPrevalidacion() {
        return estadoPrevalidacion;
    }

    public void setEstadoPrevalidacion(String estadoPrevalidacion) {
        this.estadoPrevalidacion = estadoPrevalidacion;
    }

    public String getObservacionCredito() {
        return observacionCredito;
    }

    public void setObservacionCredito(String observacionCredito) {
        this.observacionCredito = observacionCredito;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public int getNumeroLibranza() {
        return numeroLibranza;
    }

    public void setNumeroLibranza(int numeroLibranza) {
        this.numeroLibranza = numeroLibranza;
    }

    public String getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(String fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public String getTipoCr() {
        return tipoCr;
    }

    public void setTipoCr(String tipoCr) {
        this.tipoCr = tipoCr;
    }

    public int getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(int montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public int getPlazoSolicitado() {
        return plazoSolicitado;
    }

    public void setPlazoSolicitado(int plazoSolicitado) {
        this.plazoSolicitado = plazoSolicitado;
    }

    public int getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(int montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public int getCuotaAprob() {
        return cuotaAprob;
    }

    public void setCuotaAprob(int cuotaAprob) {
        this.cuotaAprob = cuotaAprob;
    }

    public int getPlazoAprob() {
        return plazoAprob;
    }

    public void setPlazoAprob(int plazoAprob) {
        this.plazoAprob = plazoAprob;
    }

    public String getObsPreaprobacion() {
        return obsPreaprobacion;
    }

    public void setObsPreaprobacion(String obsPreaprobacion) {
        this.obsPreaprobacion = obsPreaprobacion;
    }

    public String getFechaAprobacionCredito() {
        return fechaAprobacionCredito;
    }

    public void setFechaAprobacionCredito(String fechaAprobacionCredito) {
        this.fechaAprobacionCredito = fechaAprobacionCredito;
    }

    public String getObsAprobacion() {
        return obsAprobacion;
    }

    public void setObsAprobacion(String obsAprobacion) {
        this.obsAprobacion = obsAprobacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

    public int getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(int idAsesor) {
        this.idAsesor = idAsesor;
    }

    public String getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public int getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(int idRegional) {
        this.idRegional = idRegional;
    }

    public int getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(int idOficina) {
        this.idOficina = idOficina;
    }

    public String getEstadoLibranza() {
        return estadoLibranza;
    }

    public void setEstadoLibranza(String estadoLibranza) {
        this.estadoLibranza = estadoLibranza;
    }

    public String getObservicionLlamadaCliente() {
        return observicionLlamadaCliente;
    }

    public void setObservicionLlamadaCliente(String observicionLlamadaCliente) {
        this.observicionLlamadaCliente = observicionLlamadaCliente;
    }
}
