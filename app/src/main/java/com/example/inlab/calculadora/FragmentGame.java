package com.example.inlab.calculadora;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGame extends Fragment {

    public FragmentGame() {
        // Required empty public constructor
    }

    ImageView imageView0, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    ImageView imageView7, imageView8, imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_game, container, false);
        // Inflate the layout for this fragment


        imageView0 = view.findViewById(R.id.imageButton);
        imageView1 = view.findViewById(R.id.imageButton2);
        imageView2 = view.findViewById(R.id.imageButton3);
        imageView3 = view.findViewById(R.id.imageButton4);
        imageView4 = view.findViewById(R.id.imageButton5);
        imageView5 = view.findViewById(R.id.imageButton6);
        imageView6 = view.findViewById(R.id.imageButton7);
        imageView7 = view.findViewById(R.id.imageButton8);
        imageView8 = view.findViewById(R.id.imageButton9);
        imageView9 = view.findViewById(R.id.imageButton10);
        imageView10 = view.findViewById(R.id.imageButton12);
        imageView11 = view.findViewById(R.id.imageButton13);
        imageView12 = view.findViewById(R.id.imageButton14);
        imageView13 = view.findViewById(R.id.imageButton15);
        imageView14 = view.findViewById(R.id.imageButton16);
        imageView15 = view.findViewById(R.id.imageButton17);

        return view;
    }


}
