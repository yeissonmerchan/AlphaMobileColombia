package com.example.alphamobilecolombia.mvp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Persona extends RealmObject {

    @PrimaryKey
    private String cedula;
    private String nombre;

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String genero;
    private String fechaNacimiento;
    private String fechaVencimiento;
    private String factorRh;
    private String celular;
    private String fechaExpedicion;
    private String ciudad;

    public Persona() {

    }

    Persona(String _ced, String _nom, String _ape1, String _ape2,String _gen, String _fecN,String _fecV, String _fac){
        this.cedula = _ced;
        this.nombre = _nom;
        this.apellido1 = _ape1;
        this.apellido2 = _ape2;
        this.genero = _gen;
        this.fechaNacimiento = _fecN;
        this.fechaVencimiento = _fecV;
        this.factorRh = _fac;
    }

    public void setFactorRh(String _fac) {
        this.factorRh = _fac;
    }

    public void setCedula(String _ced) {
        this.cedula = _ced;
    }

    public void setNombre(String _nom) {
        this.nombre = _nom;
    }

    public void setApellido1(String _ape1) {
        this.apellido1 = _ape1;
    }

    public void setApellido2(String _ape2) {
        this.apellido2 = _ape2;
    }

    public void setGenero(String _gen) {
        this.genero = _gen;
    }

    public void setFechaNacimiento(String _fecN) {
        this.fechaNacimiento = _fecN;
    }

    public void setFechaVencimiento(String _fecV) {
        this.fechaVencimiento = _fecV;
    }

    public void setCelular(String _cel) {this.celular = _cel;}

    public String getFactorRh() {
        return this.factorRh;
    }
    public String getCedula() {
        return this.cedula;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getApellido1() {
        return this.apellido1;
    }
    public String getApellido2() {
        return this.apellido2;
    }
    public String getGenero() {
                return this.genero;
    }
    public String getGeneroId() {
        if (this.genero == "M")
        {
            return "1";
        }
        else
        {
            if (this.genero == "F")
            {
                return "2";
            }
            else
            {
                return "1";
            }
        }
    }
    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }
    public String getCelular() { return this.celular;}

    @Override
    public String toString(){
        return this.cedula +" "+
                this.apellido1 +" "+
                this.apellido2 +" "+
                this.nombre +" "+
                this.fechaNacimiento +" "+
                this.fechaVencimiento + " " +
                this.factorRh + " " +
                this.genero;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
