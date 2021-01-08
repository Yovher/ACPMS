package com.example.acpms.Modelo;

public class Actividad {

    private String id;
    private String detalleactividad;
    private String estado;
    private String fechainicio;
    private String fechafin;
    private String urlAcpm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetalleactividad() {
        return detalleactividad;
    }

    public void setDetalleactividad(String detalleactividad) {
        this.detalleactividad = detalleactividad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getUrlAcpm() {
        return urlAcpm;
    }

    public void setUrlAcpm(String urlAcpm) {
        this.urlAcpm = urlAcpm;
    }
}
