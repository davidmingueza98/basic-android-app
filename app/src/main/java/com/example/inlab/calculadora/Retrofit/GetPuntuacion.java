package com.example.inlab.calculadora.Retrofit;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPuntuacion {

@SerializedName("puntuaciones")
@Expose
private ArrayList<Puntuacione> puntuaciones = null;

public ArrayList<Puntuacione> getPuntuaciones() {
return puntuaciones;
}

public void setPuntuaciones(ArrayList<Puntuacione> puntuaciones) {
    this.puntuaciones = puntuaciones;
}

}