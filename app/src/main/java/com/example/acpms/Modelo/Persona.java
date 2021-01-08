package com.example.acpms.Modelo;

public class Persona {

    private String id;
    private String nombrecom;
    private String email;
    private String proceso1;
    private String proceso2;
    private String celular;
    private String contrasena;
    private String nuevacontrasena;

    public Persona() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrecom() {
        return nombrecom;
    }

    public void setNombrecom(String nombrecom) {
        this.nombrecom = nombrecom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProceso1() {
        return proceso1;
    }

    public void setProceso1(String proceso1) {
        this.proceso1 = proceso1;
    }

    public String getProceso2() {
        return proceso2;
    }

    public void setProceso2(String proceso2) {
        this.proceso2 = proceso2;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNuevacontrasena() {
        return nuevacontrasena;
    }

    public void setNuevacontrasena(String nuevacontrasena) {
        this.nuevacontrasena = nuevacontrasena;
    }
}
