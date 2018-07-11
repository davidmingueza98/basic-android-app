package com.example.inlab.calculadora;

import io.realm.RealmObject;

public class Puntuacion extends RealmObject{
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
