package com.example.audiolibros;

import java.io.Serializable;

public class Libro implements Serializable {
    private int id;
    private String titulo;
    private String descripcion;
    private int portada;
    private int audio;

    public Libro(int id, String titulo, String descripcion, int portada, int audio) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.portada = portada;
        this.audio = audio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPortada() {
        return portada;
    }

    public void setPortada(int portada) {
        this.portada = portada;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }
}
