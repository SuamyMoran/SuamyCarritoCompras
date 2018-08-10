package com.example.com.suamycarritocompras;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Producto implements Serializable {

    @Exclude
    private String codigo;
    private String Caracteristica;
    private String CodigodeBarra;
    private double PrecioxCaja12Unidades;
    private double PrecioxUnidad;
    private String url;

    public Producto() { }

    public Producto(String codigo, String caracteristica, String codigodeBarra, double precioxCaja12Unidades, double precioxUnidad, String url) {
        this.codigo = codigo;
        Caracteristica = caracteristica;
        CodigodeBarra = codigodeBarra;
        PrecioxCaja12Unidades = precioxCaja12Unidades;
        PrecioxUnidad = precioxUnidad;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCaracteristica() {
        return Caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        Caracteristica = caracteristica;
    }

    public String getCodigodeBarra() {
        return CodigodeBarra;
    }

    public void setCodigodeBarra(String codigodeBarra) {
        CodigodeBarra = codigodeBarra;
    }

    public double getPrecioxCaja12Unidades() {
        return PrecioxCaja12Unidades;
    }

    public void setPrecioxCaja12Unidades(double precioxCaja12Unidades) {
        PrecioxCaja12Unidades = precioxCaja12Unidades;
    }

    public double getPrecioxUnidad() {
        return PrecioxUnidad;
    }

    public void setPrecioxUnidad(double precioxUnidad) {
        PrecioxUnidad = precioxUnidad;
    }
}
