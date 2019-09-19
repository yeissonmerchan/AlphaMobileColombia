package com.example.alphamobilecolombia.data.cloud.firestore.entity;

import java.util.List;

public class VariableMotor {
    public int getIdProceso() {
        return IdProceso;
    }

    public void setIdProceso(int idProceso) {
        IdProceso = idProceso;
    }

    public int getIdTipoDato() {
        return IdTipoDato;
    }

    public void setIdTipoDato(int idTipoDato) {
        IdTipoDato = idTipoDato;
    }

    public int getIdTipoLista() {
        return IdTipoLista;
    }

    public void setIdTipoLista(int idTipoLista) {
        IdTipoLista = idTipoLista;
    }

    public int getProceso() {
        return Proceso;
    }

    public void setProceso(int proceso) {
        Proceso = proceso;
    }

    public String getNombreControl() {
        return NombreControl;
    }

    public void setNombreControl(String nombreControl) {
        NombreControl = nombreControl;
    }

    public String getNombreCampo() {
        return NombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        NombreCampo = nombreCampo;
    }

    public String getCodigoCampo() {
        return CodigoCampo;
    }

    public void setCodigoCampo(String codigoCampo) {
        CodigoCampo = codigoCampo;
    }

    public int getTipoDato() {
        return TipoDato;
    }

    public void setTipoDato(int tipoDato) {
        TipoDato = tipoDato;
    }

    public String getFormato() {
        return Formato;
    }

    public void setFormato(String formato) {
        Formato = formato;
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

    public String getValorDefecto() {
        return ValorDefecto;
    }

    public void setValorDefecto(String valorDefecto) {
        ValorDefecto = valorDefecto;
    }

    public boolean isRequerido() {
        return Requerido;
    }

    public void setRequerido(boolean requerido) {
        Requerido = requerido;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public int getLongitudMinima() {
        return LongitudMinima;
    }

    public void setLongitudMinima(int longitudMinima) {
        LongitudMinima = longitudMinima;
    }

    public int getLongitudMaxima() {
        return LongitudMaxima;
    }

    public void setLongitudMaxima(int longitudMaxima) {
        LongitudMaxima = longitudMaxima;
    }

    public boolean isConsultaLista() {
        return ConsultaLista;
    }

    public void setConsultaLista(boolean consultaLista) {
        ConsultaLista = consultaLista;
    }

    public int getTipoLista() {
        return TipoLista;
    }

    public void setTipoLista(int tipoLista) {
        TipoLista = tipoLista;
    }

    public List<ListData> getListaData() {
        return ListaData;
    }

    public void setListaData(List<ListData> listaData) {
        ListaData = listaData;
    }

    /// <summary>
    /// Get - Set IdProceso
    /// </summary>
    private int IdProceso;

    /// <summary>
    /// Get - Set IdTipoDato
    /// </summary>
    private int IdTipoDato;

    /// <summary>
    /// Get - Set IdTipoLista
    /// </summary>
    private int IdTipoLista;

    /// <summary>
    /// Get - Set Proceso
    /// </summary>
    private int Proceso;

    /// <summary>
    /// Get - Set NombreControl
    /// </summary>
    private String NombreControl;

    /// <summary>
    /// Get - Set NombreCampo
    /// </summary>
    private String NombreCampo;

    /// <summary>
    /// Get - Set CodigoCampo
    /// </summary>
    private String CodigoCampo;

    /// <summary>
    /// Get - Set TipoDato
    /// </summary>
    private int TipoDato;

    /// <summary>
    /// Get - Set Formato
    /// </summary>
    private String Formato;

    /// <summary>
    /// Get - Set ValorMinimo
    /// </summary>
    private String ValorMinimo;

    /// <summary>
    /// Get - Set ValorMaximo
    /// </summary>
    private String ValorMaximo;

    /// <summary>
    /// Get - Set Valor
    /// </summary>
    private String Valor;

    /// <summary>
    /// Get - Set ValorDefecto
    /// </summary>
    private String ValorDefecto;

    /// <summary>
    /// Get - Set Requerido
    /// </summary>
    private boolean Requerido;

    /// <summary>
    /// Get - Set Estado
    /// </summary>
    private boolean Estado;

    /// <summary>
    /// Get - Set LongitudMinima
    /// </summary>
    private int LongitudMinima;

    /// <summary>
    /// Get - Set LongitudMaxima
    /// </summary>
    private int LongitudMaxima;

    /// <summary>
    /// Get - Set ConsultaLista
    /// </summary>
    private boolean ConsultaLista;

    /// <summary>
    /// Get - Set ConsultaLista
    /// </summary>
    private int TipoLista;
    /// <summary>
    /// Get - Set ListaData
    /// </summary>
    private List<ListData> ListaData;
}
