package com.example.inlab.calculadora;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class Main2Activity extends AppCompatActivity {

    Button button_login, button_r;
    EditText editText_name, editText_password;
    private Realm realm;
    private RealmResults<User> userResults;

    boolean error = false;

    int mId = 3; //Identificador notificación

    NotificationManager mNotificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button_login = findViewById(R.id.button20);
        button_r = findViewById(R.id.button19);
        editText_name = findViewById(R.id.editText2);
        editText_password = findViewById(R.id.editText4);

        initRealm(); //Inicializamos la base de datos


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = editText_name.getText().toString();
                String password = editText_password.getText().toString();
                if (!name.isEmpty() && !password.isEmpty()) {
                    userResults = realm.where(User.class).equalTo("username",name).findAll(); //search username in database
                    if (!userResults.isEmpty() && userResults.first().getPassword().equals(password)){ //if the password match then login
                        if( mNotificationManager!= null) mNotificationManager.cancel(mId); //cancel the error notificaction if necessary
                        Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                        startActivity(intent);
                    }
                    else error = true;
                }
                else error = true;

                if (error){
                    //Toast.makeText(getApplicationContext(),"Login incorrecto xdd", Toast.LENGTH_LONG).show();
                    //Instanciamos Notification Manager
                    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    // A PARTIR DE ANDROID OREO, LAS NOTIFICACIONES REQUIEREN DE UN CANAL ASOCIADO
                    String NOTIFICATION_CHANNEL_ID = "1";
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Error")
                            .setContentText("Incorrect login, please try again");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                        notificationChannel.setDescription("Channel description");
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.RED);
                        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                        notificationChannel.enableVibration(true);
                        mNotificationManager.createNotificationChannel(notificationChannel);
                    }

                    Notification noti = mBuilder.build();

                    noti.flags |= Notification.FLAG_INSISTENT;
                    noti.flags |= Notification.FLAG_NO_CLEAR;
                    noti.flags |= Notification.FLAG_SHOW_LIGHTS;

                    mNotificationManager.notify(mId, noti);

                }
            }
        });

        button_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void initRealm(){
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
    }
}
