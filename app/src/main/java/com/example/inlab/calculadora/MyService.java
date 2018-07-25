package com.example.inlab.calculadora;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

    @Override
    public void onCreate(){
        //TODO inicializaciones únicas
       // mediaPlayer = MediaPlayer.create(context, R.raw.song_name);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        //No es necesario nada más
        return null;
    }

    @Override
    public void onDestroy(){
        //TODO código para liberar recursos
        //StopSelf() o StopService()
    }
}

