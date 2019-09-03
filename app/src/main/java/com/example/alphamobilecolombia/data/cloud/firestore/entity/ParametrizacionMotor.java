package com.example.alphamobilecolombia.data.cloud.firestore.entity;

import com.example.alphamobilecolombia.data.cloud.firestore.entity.enumerator.Motor;

import java.util.List;

public class ParametrizacionMotor {
    /// <summary>
    /// Get - Set IdParameter
    /// </summary>
    private String IdParameter;
    /// <summary>
    /// Get - Set Proceso
    /// </summary>
    private int Proceso;

    /// <summary>
    /// Get - Set FechaUltimaActualizacion
    /// </summary>
    private String FechaUltimaActualizacion;

    /// <summary>
    /// Get - Set Variables
    /// </summary>
    private List<VariableMotor> Variables;

    /// <summary>
    /// Get - Set Reglas
    /// </summary>
    private List<Regla> Reglas;

    /// <summary>
    /// Get - Set Reglas
    /// </summary>
    private List<ConfiguracionMotor> Configuracion;


    public String getIdParameter() {
        return IdParameter;
    }

    public void setIdParameter(String idParameter) {
        IdParameter = idParameter;
    }

    public int getProceso() {
        return Proceso;
    }

    public void setProceso(int proceso) {
        Proceso = proceso;
    }

    public String getFechaUltimaActualizacion() {
        return FechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(String fechaUltimaActualizacion) {
        FechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public List<VariableMotor> getVariables() {
        return Variables;
    }

    public void setVariables(List<VariableMotor> variables) {
        Variables = variables;
    }

    public List<Regla> getReglas() {
        return Reglas;
    }

    public void setReglas(List<Regla> reglas) {
        Reglas = reglas;
    }

    public List<ConfiguracionMotor> getConfiguracion() {
        return Configuracion;
    }

    public void setConfiguracion(List<ConfiguracionMotor> configuracion) {
        Configuracion = configuracion;
    }
}
