package com.example.datossesion;

public class Usuario {
    private int idAsesor;
    private String nombres;
    private String aPaterno;
    private String aMaterno;
    private String correo;

    // Constructor
    public Usuario(int idAsesor, String nombres, String aPaterno, String aMaterno, String correo) {
        this.idAsesor = idAsesor;
        this.nombres = nombres;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.correo = correo;
    }

    // Getters
    public int getIdAsesor() {
        return idAsesor;
    }

    public String getNombres() {
        return nombres;
    }

    public String getaPaterno() {
        return aPaterno;
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public String getCorreo() {
        return correo;
    }
}