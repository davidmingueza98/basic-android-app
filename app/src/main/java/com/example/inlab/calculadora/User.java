package com.example.inlab.calculadora;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject{
    private String username;
    private String password;
    private RealmList<Puntuacion> puntuaciones;
    private String fecha_record;
    private int record;
    private int record_time;

    public User(){};

    public User(String username, String password, int record, int record_time, String fecha_record){
        this.username = username;
        this.password = password;
        this.record = record;
        this.record_time = record_time;
        this.fecha_record = fecha_record;
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

    public int getRecord() {
        return record;
    }

    public int getRecord_time() {
        return record_time;
    }

    public String getFecha_record() {
        return fecha_record;
    }


    public void setRecord(int record) {
        this.record = record;
    }

    public void setRecord_time(int record_time) {
        this.record_time = record_time;
    }

    public void setFecha_record(String fecha_record) {
        this.fecha_record = fecha_record;
    }

}
