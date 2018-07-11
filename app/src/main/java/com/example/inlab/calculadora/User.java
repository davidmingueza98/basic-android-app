package com.example.inlab.calculadora;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject{
    private String username;
    private String password;
    private RealmList<Puntuacion> puntuaciones;

    public User(){};

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RealmList<Puntuacion> getPuntuaciones() {
        return puntuaciones;
    }

}
