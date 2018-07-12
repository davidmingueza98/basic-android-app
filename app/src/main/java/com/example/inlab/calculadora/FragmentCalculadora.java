package com.example.inlab.calculadora;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCalculadora extends Fragment {
    Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    Button button_add,button_sub,button_div,button_mult,button_del,button_eq,button_coma,button_pow,button_sqrt;

    FloatingActionButton call;

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

    boolean sqrt = false;

    Double d=0.0;
    String num2 = "";
    String operation = "";
    TextView textViewResult;
    TextView textViewOp;

    public FragmentCalculadora() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_calculadora, container, false);
        // Inflate the layout for this fragment


        button0 = view.findViewById(R.id.button16);
        button1 = view.findViewById(R.id.button11);
        button2 = view.findViewById(R.id.button12);
        button3 = view.findViewById(R.id.button13);
        button4 = view.findViewById(R.id.button10);
        button5 = view.findViewById(R.id.button4);
        button6 = view.findViewById(R.id.button8);
        button7 = view.findViewById(R.id.button3);
        button8 = view.findViewById(R.id.button5);
        button9 = view.findViewById(R.id.button6);
        button_add = view.findViewById(R.id.button18);
        button_sub = view.findViewById(R.id.button14);
        button_div = view.findViewById(R.id.button7);
        button_mult = view.findViewById(R.id.button9);
        button_del = view.findViewById(R.id.button17);
        button_eq = view.findViewById(R.id.button21);
        button_coma = view.findViewById(R.id.button15);
        button_pow = view.findViewById(R.id.button23);
        button_sqrt = view.findViewById(R.id.button2);
        call = view.findViewById(R.id.floatingActionButton);

        textViewResult = view.findViewById(R.id.textViewResult);
        textViewOp = view.findViewById(R.id.textViewOp);



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

                     if ((op || sqrt) && !nan) {
                        //int d2 = d.intValue();
                        //textViewResult.setText(String.valueOf(d2));
                        textViewResult.setText(String.format("%.6f", d));
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
                if (number_put && !sqrt) {
                    Button b = (Button) view; // Castear la vista del onClick a botón
                    textViewOp.append(b.getText());
                    operation = b.getText().toString(); // Leer el texto de un botón
                    num2 = "";
                    result = d;
                    first = true;
                    first_zero = false;
                    op = true;

                }
            }
        };

        button_add.setOnClickListener(appendOperation);
        button_sub.setOnClickListener(appendOperation);
        button_div.setOnClickListener(appendOperation);
        button_mult.setOnClickListener(appendOperation);
        button_pow.setOnClickListener(appendOperation);


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
                    sqrt = false;
                }
                else if (equal){
                    if(nan) {
                        String error = "No se puede dividir entre 0, presione AC";
                        textViewResult.setText(error);
                    }
                    else {
                        textViewOp.setText(textViewResult.getText().toString());
                        number_put = true;
                        textViewResult.setText("");
                        equal = false;
                        op = false;
                        point = false;
                        sqrt = false;
                        operation = "";

                        if (d == 0) first_zero = true;
                        num2 = String.valueOf(d.intValue());
                        result = 0.0;
                    }
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
                    if (nan) nan = false;
                    point = true;
                    textViewOp.append(".");
                    num2 += '.';
                }
            }
        });

        button_sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (first && !op && !number_put) {
                    Button b = (Button) view; // Castear la vista del onClick a botón
                    textViewOp.append(b.getText());
                    sqrt = true;
                }
            }
        });

        call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux = textViewOp.getText().toString();
                if (number_put && !point && !op && !sqrt) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + aux));
                    startActivity(intent);
                }
                else Toast.makeText(getActivity(),"Incorrect number", Toast.LENGTH_LONG).show();

            }
        });



        return view;
    }

    private Double performOperation() {
        Double d = Double.parseDouble(this.num2);
        if (operation.equals("+")) {
            d += result;
        } else if (operation.equals("-")) {
            d = result - d;
        } else if (Objects.equals("/", operation)) {
            if (d==0) {
                nan = true;
                equal = true;
            }
            else d = result / d;
        } else if (Objects.equals(operation, "x")) {
            d *= result;
        } else if (Objects.equals(operation, "^")) {
            d = Math.pow(result,d);
        } else if (sqrt){
            d = Math.sqrt(d);
        }
        return d;
    }


    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putString("result", textViewOp.getText().toString());
        outstate.putString("result2", textViewResult.getText().toString());
        //Log.v("result", t.getText().toString());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            textViewOp.setText(savedInstanceState.getString("result"));
            textViewResult.setText(savedInstanceState.getString("result2"));
        }
    }

}
