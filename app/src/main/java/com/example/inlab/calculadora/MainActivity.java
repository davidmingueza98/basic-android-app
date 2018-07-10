package com.example.inlab.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    Button button_add,button_sub,button_div,button_mult,button_del,button_eq,button_coma;

    //Intent intent = new Intent(Intent: ACTION_DIAL, Uri.parse(NUMERO_TELEFONO)).

    boolean equal = false;
    boolean op = false;
    boolean number_put = false;
    Double result=0.0;
    boolean nan = false;

    boolean first_zero = false;
    boolean first = true;

    boolean negative = false;
    boolean point = false;

    Double d=0.0;
    String num2 = "";
    String operation = "";
    TextView textViewResult;
    TextView textViewOp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button0 = findViewById(R.id.button16);
        button1 = findViewById(R.id.button11);
        button2 = findViewById(R.id.button12);
        button3 = findViewById(R.id.button13);
        button4 = findViewById(R.id.button10);
        button5 = findViewById(R.id.button4);
        button6 = findViewById(R.id.button8);
        button7 = findViewById(R.id.button3);
        button8 = findViewById(R.id.button5);
        button9 = findViewById(R.id.button6);
        button_add = findViewById(R.id.button18);
        button_sub = findViewById(R.id.button14);
        button_div = findViewById(R.id.button7);
        button_mult = findViewById(R.id.button9);
        button_del = findViewById(R.id.button17);
        button_eq = findViewById(R.id.button21);
        button_coma = findViewById(R.id.button15);

        textViewResult = findViewById(R.id.textViewResult);
        textViewOp = findViewById(R.id.textViewOp);


        // OnClick botón número
        View.OnClickListener appendNumber = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_put = true;
                Button b = (Button) view; // Castear la vista del onClick a botón

                String number = b.getText().toString();
                if (first && number.equals("0")) first_zero = true;

                if(!first_zero || first) {
                    if (first) first = false;
                    textViewOp.append(b.getText());
                    num2 += number; // Leer el texto de un botón

                   //if(negative) num2 = "-" + num2;

                    d = performOperation();

                    if (nan) {
                        String error = "No se puede dividir entre 0, presione AC";
                        textViewResult.setText(error);
                    } else if (op) {
                        //int d2 = d.intValue();
                        //textViewResult.setText(String.valueOf(d2));
                        textViewResult.setText(String.valueOf(d));
                        equal = true;
                        point = false;
                    }
                }
            }
        };

        button0.setOnClickListener(appendNumber);
        button1.setOnClickListener(appendNumber);
        button2.setOnClickListener(appendNumber);
        button3.setOnClickListener(appendNumber);
        button4.setOnClickListener(appendNumber);
        button5.setOnClickListener(appendNumber);
        button6.setOnClickListener(appendNumber);
        button7.setOnClickListener(appendNumber);
        button8.setOnClickListener(appendNumber);
        button9.setOnClickListener(appendNumber);




        View.OnClickListener appendOperation = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number_put) {
                    Button b = (Button) view; // Castear la vista del onClick a botón
                    textViewOp.append(b.getText());
                    String aux = b.getText().toString();
                    if (op && aux.equals("-")) negative = true;
                    else {
                        operation = b.getText().toString(); // Leer el texto de un botón
                        num2 = "";
                        result = d;
                        first = true;
                        first_zero = false;
                        op = true;
                    }
                }
            }
        };

        button_add.setOnClickListener(appendOperation);
        button_sub.setOnClickListener(appendOperation);
        button_div.setOnClickListener(appendOperation);
        button_mult.setOnClickListener(appendOperation);


        View.OnClickListener appendClear = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view; // Castear la vista del onClick a botón
                String what = b.getText().toString(); // Leer el texto de un botón
                if (what.equals("AC")){
                    textViewOp.setText("");
                    textViewResult.setText("");
                    num2 = "";
                    result=0.0;
                    d=0.0;
                    operation = "";
                    number_put = false;
                    point = false;
                    equal = false;
                    op=false;
                    nan = false;
                    first = true;
                    first_zero=false;
                }
                else if (equal){
                    textViewOp.setText(textViewResult.getText().toString());
                    number_put=true;
                    textViewResult.setText("");
                    equal=false;
                    op=false;
                    point = false;
                    operation = "";

                    if (d==0) first_zero=true;
                    num2 = String.valueOf(d.intValue());
                    result = 0.0;

                }
            }
        };

        button_del.setOnClickListener(appendClear);
        button_eq.setOnClickListener(appendClear);

        button_coma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(!point && number_put){
                    if (first_zero) first_zero=false;
                    point = true;
                    textViewOp.append(".");
                    num2 += '.';
                }
            }
        });

    }


    private Double performOperation() {
        Double d = Double.parseDouble(this.num2);
        if (operation.equals("+")) {
            d += result;
        } else if (operation.equals("-")) {
            d = result - d;
        } else if (Objects.equals("/", operation)) {
            if (d==0) nan = true;
            else d = result / d;
        } else if (Objects.equals(operation, "x")) {
            d *= result;
        }
        return d;
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putString("result", textViewOp.getText().toString());
        outstate.putString("result2", textViewResult.getText().toString());
        //Log.v("result", t.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        textViewOp.setText(savedInstanceState.getString("result"));
        textViewResult.setText(savedInstanceState.getString("result2"));
        //Log.v("retrieving",savedInstanceState.getString("result"));
    }

}
