package com.example.inlab.calculadora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterActivity extends AppCompatActivity {

    Button button;
    EditText editText_username, editText_pw, editText_confirmpw;

    private Realm realm;
    private RealmResults<User> userResults;

    boolean error = false;

    /*
    String PREFS_NAME = "PREFS_NAME";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.button);
        editText_username = findViewById(R.id.editText);
        editText_pw = findViewById(R.id.editText3);
        editText_confirmpw = findViewById(R.id.editText5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_username.getText().toString();
                String password = editText_pw.getText().toString();
                String confirm_password = editText_confirmpw.getText().toString();
                if (password.equals(confirm_password) && !name.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty()) {
                    /*
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    editor = settings.edit();
                    editor.putString("username", name);
                    editor.putString("password", password);
                    editor.apply();
                    */
                    Realm.init(getApplicationContext());
                    realm = Realm.getDefaultInstance();
                    userResults = realm.where(User.class).equalTo("username", name).findAll(); //search username in database
                    if (userResults.isEmpty()) {//empty=not results so it does not exist
                        if (error) error = false; //The registration is correct
                        User rookie = new User(name,password);
                        saveUserToRealm(rookie); //store in the database

                        View.OnClickListener myOnClickListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        };

                        //Creamos una notificación snackbar
                        // parentLayout: ViewGroup donde lo queremos mostrar
                        // R.string.snackbar_text texto a mostrar definido en strings.xml

                        ViewGroup layout = findViewById(R.id.layout);
                        Snackbar.make(layout, "WELCOME TO MY APP " + name + "👽", Snackbar.LENGTH_LONG)
                                .setAction("LOG IN", myOnClickListener)
                                .show(); // Importante!!! No olvidar mostrar la Snackbar.

                    }
                    else error = true;
                }
                else error = true;

                if (error){
                    Toast.makeText(getApplicationContext(), "Sorry! Incorrect Registration", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveUserToRealm(User user) {
        realm.beginTransaction();
        // Se añade el usuario creado a Realm
        User managedUser = realm.copyToRealm(user);
        Puntuacion punt = realm.createObject(Puntuacion.class);
        punt.setFecha("1/1/1");
        punt.setPuntuacion("0");

        managedUser.getPuntuaciones().add(punt);
        realm.commitTransaction();
    }
}