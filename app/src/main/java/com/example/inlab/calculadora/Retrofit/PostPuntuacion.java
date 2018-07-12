package com.example.inlab.calculadora.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostPuntuacion {

@SerializedName("puntuacion")
@Expose
private Puntuacione puntuacion;

public Puntuacione getPuntuacion() {
return puntuacion;
}

public void setPuntuacion(Puntuacione puntuacion) {
this.puntuacion = puntuacion;
}

}