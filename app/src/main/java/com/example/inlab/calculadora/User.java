package com.example.inlab.calculadora;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject{
    private String username;
    private String password;
    private RealmList<Puntuacion> puntuaciones;
}
