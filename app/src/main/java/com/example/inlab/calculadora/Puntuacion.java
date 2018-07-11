package com.example.inlab.calculadora;

public class Puntuacion {
    private String fecha;
    private String puntuacion;

    public Puntuacion(){
    }

    public Puntuacion (String fecha, String puntuacion){
        this.fecha=fecha;
        this.puntuacion=puntuacion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }
}
