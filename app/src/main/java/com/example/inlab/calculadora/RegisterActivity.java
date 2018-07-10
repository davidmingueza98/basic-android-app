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

public class RegisterActivity extends AppCompatActivity {

    Button button;
    EditText editText_username, editText_pw, editText_confirmpw;

    String PREFS_NAME = "PREFS_NAME";

    SharedPreferences settings;
    SharedPreferences.Editor editor;
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
            public void onClick(View v){
                String name = editText_username.getText().toString();
                String password = editText_pw.getText().toString();
                if (password.equals(editText_confirmpw.getText().toString()) && !name.isEmpty()) {
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    editor = settings.edit();
                    editor.putString("username", name);
                    editor.putString("password", password);
                    editor.apply();

                    //Creamos una notificación snackbar
                    // parentLayout: ViewGroup donde lo queremos mostrar
                    // R.string.snackbar_text texto a mostrar definido en strings.xml
                    View.OnClickListener myOnClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    };

                    ViewGroup layout = findViewById(R.id.layout);
                    Snackbar.make(layout, "REGISTRO CORRECTO " + "👽", Snackbar.LENGTH_LONG)
                            .setAction("LOG IN", myOnClickListener)
                            .show(); // Importante!!! No olvidar mostrar la Snackbar.

                }
                else{
                    Toast.makeText(getApplicationContext(),"Registro incorrecto XD",  Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
