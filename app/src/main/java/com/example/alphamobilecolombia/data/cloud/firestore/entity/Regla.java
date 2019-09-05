package com.example.alphamobilecolombia.data.cloud.firestore.entity;

import io.realm.RealmObject;

public class Regla extends RealmObject {
    /// <summary>
    /// Get - Set IdProceso
    /// </summary>
    private int IdProceso;

    /// <summary>
    /// Get - Set Proceso
    /// </summary>
    private int Proceso;

    /// <summary>
    /// Get - Set NombreRegla
    /// </summary>
    private String NombreRegla;

    /// <summary>
    /// Get - Set CodigoRegla
    /// </summary>
    private String CodigoRegla;

    /// <summary>
    /// Get - Set Estado
    /// </summary>
    private boolean Estado;

    /// <summary>
    /// Get - Set IdOperadorLogico
    /// </summary>
    private int IdOperadorLogico;

    /// <summary>
    /// Get - Set Operador
    /// </summary>
    private int Operador;

    /// <summary>
    /// Get - Set CodigoCampoValidar
    /// </summary>
    private String CodigoCampoValidar;

    /// <summary>
    /// Get - Set CodigoCampoDependiente
    /// </summary>
    private String CodigoCampoDependiente;

    /// <summary>
    /// Get - Set DependeValorCampo
    /// </summary>
    private boolean DependeValorCampo;

    /// <summary>
    /// Get - Set ValorDependiente
    /// </summary>
    private String ValorDependiente;

    /// <summary>
    /// Get - Set ValorMinimo
    /// </summary>
    private String ValorMinimo;

    /// <summary>
    /// Get - Set ValorMaximo
    /// </summary>
    private String ValorMaximo;

    public int getIdProceso() {
        return IdProceso;
    }

    public void setIdProceso(int idProceso) {
        IdProceso = idProceso;
    }

    public int getProceso() {
        return Proceso;
    }

    public void setProceso(int proceso) {
        Proceso = proceso;
    }

    public String getNombreRegla() {
        return NombreRegla;
    }

    public void setNombreRegla(String nombreRegla) {
        NombreRegla = nombreRegla;
    }

    public String getCodigoRegla() {
        return CodigoRegla;
    }

    public void setCodigoRegla(String codigoRegla) {
        CodigoRegla = codigoRegla;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public int getIdOperadorLogico() {
        return IdOperadorLogico;
    }

    public void setIdOperadorLogico(int idOperadorLogico) {
        IdOperadorLogico = idOperadorLogico;
    }

    public int getOperador() {
        return Operador;
    }

    public void setOperador(int operador) {
        Operador = operador;
    }

    public String getCodigoCampoValidar() {
        return CodigoCampoValidar;
    }

    public void setCodigoCampoValidar(String codigoCampoValidar) {
        CodigoCampoValidar = codigoCampoValidar;
    }

    public String getCodigoCampoDependiente() {
        return CodigoCampoDependiente;
    }

    public void setCodigoCampoDependiente(String codigoCampoDependiente) {
        CodigoCampoDependiente = codigoCampoDependiente;
    }

    public boolean isDependeValorCampo() {
        return DependeValorCampo;
    }

    public void setDependeValorCampo(boolean dependeValorCampo) {
        DependeValorCampo = dependeValorCampo;
    }

    public String getValorDependiente() {
        return ValorDependiente;
    }

    public void setValorDependiente(String valorDependiente) {
        ValorDependiente = valorDependiente;
    }

    public String getValorMinimo() {
        return ValorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        ValorMinimo = valorMinimo;
    }

    public String getValorMaximo() {
        return ValorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        ValorMaximo = valorMaximo;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    /// <summary>
    /// Get - Set Valor
    /// </summary>
    private String Valor;

    /// <summary>
    /// Get - Set Mensaje
    /// </summary>
    private String Mensaje;
}
