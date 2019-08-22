package com.example.alphamobilecolombia.data.remote.Models.Response;

public class ApiResponse {
    private Integer codigoRespuesta;
    private String mensaje;

    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Integer codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(Long codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Long codigoTransaccion;
    private Object data;
}
