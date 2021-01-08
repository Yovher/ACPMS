package com.example.acpms.Modelo;

public class Acpms {

    private String id;
    private String descripcionacpm;
    private String tipo1acpm;
    private String urlAcpm;
    private String idPersona;

    public Acpms() {
    }

    public String getIdPersona() {
        return idPersona;
    }

    public Acpms(String id, String descripcionacpm, String tipo1acpm, String urlAcpm, String idPersona) {
        this.id = id;
        this.descripcionacpm = descripcionacpm;
        this.tipo1acpm = tipo1acpm;
        this.urlAcpm = urlAcpm;
        this.idPersona = idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcionacpm() {
        return descripcionacpm;
    }

    public void setDescripcionacpm(String descripcionacpm) {
        this.descripcionacpm = descripcionacpm;
    }

    public String getTipo1acpm() {
        return tipo1acpm;
    }

    public void setTipo1acpm(String tipo1acpm) {
        this.tipo1acpm = tipo1acpm;
    }

    public String getUrlAcpm() {
        return urlAcpm;
    }

    public void setUrlAcpm(String urlAcpm) {
        this.urlAcpm = urlAcpm;
    }
}
