package com.example.alphamobilecolombia.data.remote.Models.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PostQueryCredit implements Parcelable {
    @SerializedName("idSujeto")
    private String IdSubject;

    @SerializedName("idCredito")
    private String IdCredit;

    @SerializedName("estadoGeneral")
    private String GeneralState;


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
    private String obsPrevalidacion;
    private String estadoPrevalidacion;
    private String observacionCredito;
    private String numeroSolicitud;
    private String numeroLibranza;
    private String fechaRadicacion;
    private String tipoCr;
    private String montoSolicitado;
    private String plazoSolicitado;
    private String montoAprobado;
    private String cuotaAprob;
    private String plazoAprob;
    private String obsPreaprobacion;
    private String fechaAprobacionCredito;
    private String obsAprobacion;
    private String estado;
    private String fabrica;
    private String idAsesor;
    private String fechaDesembolso;
    private String idCoordinador;
    private String idRegional;
    private String idOficina;
    private String estadoLibranza;
    private String observicionLlamadaCliente;

    protected PostQueryCredit(Parcel in) {
        IdSubject = in.readString();
        IdCredit = in.readString();
        GeneralState = in.readString();
        regional = in.readString();
        oficina = in.readString();
        coordinador = in.readString();
        asesor = in.readString();
        pagaduria = in.readString();
        documentoCliente = in.readString();
        cliente = in.readString();
        fechaEnvioPrevalidacion = in.readString();
        montoSugerido = in.readString();
        cuotaSug = in.readString();
        plazoSugerido = in.readString();
        fechaPrevalidacion = in.readString();
        obsPrevalidacion = in.readString();
        estadoPrevalidacion = in.readString();
        observacionCredito = in.readString();
        numeroSolicitud = in.readString();
        numeroLibranza = in.readString();
        fechaRadicacion = in.readString();
        tipoCr = in.readString();
        montoSolicitado = in.readString();
        plazoSolicitado = in.readString();
        montoAprobado = in.readString();
        cuotaAprob = in.readString();
        plazoAprob = in.readString();
        obsPreaprobacion = in.readString();
        fechaAprobacionCredito = in.readString();
        obsAprobacion = in.readString();
        estado = in.readString();
        fabrica = in.readString();
        idAsesor = in.readString();
        fechaDesembolso = in.readString();
        idCoordinador = in.readString();
        idRegional = in.readString();
        idOficina = in.readString();
        estadoLibranza = in.readString();
        observicionLlamadaCliente = in.readString();
    }

    public static final Creator<PostQueryCredit> CREATOR = new Creator<PostQueryCredit>() {
        @Override
        public PostQueryCredit createFromParcel(Parcel in) {
            return new PostQueryCredit(in);
        }

        @Override
        public PostQueryCredit[] newArray(int size) {
            return new PostQueryCredit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(IdSubject);
        parcel.writeString(IdCredit);
        parcel.writeString(GeneralState);
        parcel.writeString(regional);
        parcel.writeString(oficina);
        parcel.writeString(coordinador);
        parcel.writeString(asesor);
        parcel.writeString(pagaduria);
        parcel.writeString(documentoCliente);
        parcel.writeString(cliente);
        parcel.writeString(fechaEnvioPrevalidacion);
        parcel.writeString(montoSugerido);
        parcel.writeString(cuotaSug);
        parcel.writeString(plazoSugerido);
        parcel.writeString(fechaPrevalidacion);
        parcel.writeString(obsPrevalidacion);
        parcel.writeString(estadoPrevalidacion);
        parcel.writeString(observacionCredito);
        parcel.writeString(numeroSolicitud);
        parcel.writeString(numeroLibranza);
        parcel.writeString(fechaRadicacion);
        parcel.writeString(tipoCr);
        parcel.writeString(montoSolicitado);
        parcel.writeString(plazoSolicitado);
        parcel.writeString(montoAprobado);
        parcel.writeString(cuotaAprob);
        parcel.writeString(plazoAprob);
        parcel.writeString(obsPreaprobacion);
        parcel.writeString(fechaAprobacionCredito);
        parcel.writeString(obsAprobacion);
        parcel.writeString(estado);
        parcel.writeString(fabrica);
        parcel.writeString(idAsesor);
        parcel.writeString(fechaDesembolso);
        parcel.writeString(idCoordinador);
        parcel.writeString(idRegional);
        parcel.writeString(idOficina);
        parcel.writeString(estadoLibranza);
        parcel.writeString(observicionLlamadaCliente);
    }

    public String getIdSubject() {
        return IdSubject;
    }

    public void setIdSubject(String idSubject) {
        IdSubject = idSubject;
    }

    public String getIdCredit() {
        return IdCredit;
    }

    public void setIdCredit(String idCredit) {
        IdCredit = idCredit;
    }

    public String getGeneralState() {
        return GeneralState;
    }

    public void setGeneralState(String generalState) {
        GeneralState = generalState;
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

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getNumeroLibranza() {
        return numeroLibranza;
    }

    public void setNumeroLibranza(String numeroLibranza) {
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

    public String getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public String getPlazoSolicitado() {
        return plazoSolicitado;
    }

    public void setPlazoSolicitado(String plazoSolicitado) {
        this.plazoSolicitado = plazoSolicitado;
    }

    public String getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(String montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public String getCuotaAprob() {
        return cuotaAprob;
    }

    public void setCuotaAprob(String cuotaAprob) {
        this.cuotaAprob = cuotaAprob;
    }

    public String getPlazoAprob() {
        return plazoAprob;
    }

    public void setPlazoAprob(String plazoAprob) {
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

    public String getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(String idAsesor) {
        this.idAsesor = idAsesor;
    }

    public String getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public String getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public String getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(String idRegional) {
        this.idRegional = idRegional;
    }

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
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

    public static Creator<PostQueryCredit> getCREATOR() {
        return CREATOR;
    }
}
