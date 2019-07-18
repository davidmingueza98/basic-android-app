package com.example.inlab.calculadora;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Configuration;
import com.example.inlab.calculadora.Retrofit.APIService;
import com.example.inlab.calculadora.Retrofit.ApiUtils;
import com.example.inlab.calculadora.Retrofit.PostPuntuacion;
import com.example.inlab.calculadora.Retrofit.Puntuacione;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGame extends Fragment {


    public FragmentGame() {
        // Required empty public constructor
    }

    ImageView imageView0, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    ImageView imageView7, imageView8, imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15;

    TextView textView;
    boolean first = true;
    boolean started_game = false;
    boolean stopped = false;
    boolean click_enabled = true;
    boolean first_access = true;
    int parejas = 0;
    int movimientos = 0;
    int tiempo = 0;
    ImageView aux = imageView4;

    Handler handler;

    BackgroundCount bc = null;

    private Realm realm;

    String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_game, container, false);
        // Inflate the layout for this fragment

        landscape();
        setHasOptionsMenu(true); //para el menu de start stop

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

        textView = view.findViewById(R.id.textView3);

        handler = new Handler();

        Bundle recieved = getArguments(); //el que habíamos pasado
        user = recieved.getString("username");


        final Map<ImageView, Integer> Assignation = new HashMap<ImageView, Integer>(); //a cada imagen le asociamos un identificador de foto
        final Map<ImageView, Boolean> Volteada = new HashMap<ImageView, Boolean>();
        final int a[] = {R.drawable.hitler, R.drawable.hitler, R.drawable.kappa, R.drawable.kappa,
                        R.drawable.patricio, R.drawable.patricio, R.drawable.putin, R.drawable.putin,
                        R.drawable.rajoy, R.drawable.rajoy, R.drawable.stalin, R.drawable.stalin,
                        R.drawable.trump, R.drawable.trump, R.drawable.willyrex, R.drawable.willyrex};

        shuffleArray(a);
        Assignation.put(imageView0,a[0]);
        Assignation.put(imageView1,a[1]);
        Assignation.put(imageView2,a[2]);
        Assignation.put(imageView3,a[3]);
        Assignation.put(imageView4,a[4]);
        Assignation.put(imageView5,a[5]);
        Assignation.put(imageView6,a[6]);
        Assignation.put(imageView7,a[7]);
        Assignation.put(imageView8,a[8]);
        Assignation.put(imageView9,a[9]);
        Assignation.put(imageView10,a[10]);
        Assignation.put(imageView11,a[11]);
        Assignation.put(imageView12,a[12]);
        Assignation.put(imageView13,a[13]);
        Assignation.put(imageView14,a[14]);
        Assignation.put(imageView15,a[15]);

        Volteada.put(imageView0,false);
        Volteada.put(imageView1,false);
        Volteada.put(imageView2,false);
        Volteada.put(imageView3,false);
        Volteada.put(imageView4,false);
        Volteada.put(imageView5,false);
        Volteada.put(imageView6,false);
        Volteada.put(imageView7,false);
        Volteada.put(imageView8,false);
        Volteada.put(imageView9,false);
        Volteada.put(imageView10,false);
        Volteada.put(imageView11,false);
        Volteada.put(imageView12,false);
        Volteada.put(imageView13,false);
        Volteada.put(imageView14,false);
        Volteada.put(imageView15,false);


        final CoolImageFlipper flipper = new CoolImageFlipper(getContext());



        View.OnClickListener flipCard = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = (ImageView) view;
                if(!started_game || stopped) Toast.makeText(getActivity(), "Please, press start", Toast.LENGTH_SHORT).show();
                if (!Volteada.get(iv) && started_game && click_enabled) {
                    Drawable d = getResources().getDrawable(Assignation.get(iv)); //pasa el int a Drawable
                    flipper.flipImage(d, iv); //flip
                    if (first) {
                        aux = iv;
                        first = false;
                        Volteada.remove(iv);
                        Volteada.put(iv,true);
                    } else {
                        click_enabled = false;
                        first = true; //vuelve a intentar hacer pareja
                        ++movimientos;
                        if (!Assignation.get(aux).equals(Assignation.get(iv))){
                            d = getResources().getDrawable(R.drawable.carta_abajo);

                            retarded_animation(flipper,d,iv,aux);

                            Volteada.remove(iv);
                            Volteada.put(iv,false);

                            Volteada.remove(aux);
                            Volteada.put(aux, false);
                        }
                        else{
                            ++parejas;
                            Volteada.remove(iv);
                            Volteada.put(iv, true);
                            click_enabled = true;
                            if(parejas == 8) finish_game();
                        }
                    }
                }

            }
        };

        imageView0.setOnClickListener(flipCard);
        imageView1.setOnClickListener(flipCard);
        imageView2.setOnClickListener(flipCard);
        imageView3.setOnClickListener(flipCard);
        imageView4.setOnClickListener(flipCard);
        imageView5.setOnClickListener(flipCard);
        imageView6.setOnClickListener(flipCard);
        imageView7.setOnClickListener(flipCard);
        imageView8.setOnClickListener(flipCard);
        imageView9.setOnClickListener(flipCard);
        imageView10.setOnClickListener(flipCard);
        imageView11.setOnClickListener(flipCard);
        imageView12.setOnClickListener(flipCard);
        imageView13.setOnClickListener(flipCard);
        imageView14.setOnClickListener(flipCard);
        imageView15.setOnClickListener(flipCard);






        /*
        imageView0.setImageResource(R.drawable.hitler);
        imageView1.setImageResource(R.drawable.stalin);
        imageView2.setImageResource(R.drawable.willyrex);
        imageView3.setImageResource(R.drawable.trump);
        imageView4.setImageResource(R.drawable.kappa);
        imageView15.setImageResource(R.drawable.patricio);
        imageView9.setImageResource(R.drawable.putin);
        imageView10.setImageResource(R.drawable.rajoy);
        */

        return view;
    }

    void retarded_animation(final CoolImageFlipper flipper, final Drawable d, final ImageView iv, final ImageView aux){
        new Thread(new Runnable(){
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flipper.flipImage(d, iv); //flip 1
                        flipper.flipImage(d, aux); //flip 2
                        click_enabled = true;
                    }
                }, 1000);
            }
        }).start();
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

    }


    void finish_game(){
        started_game = false;

        /**
        //Enviamos la puntuacion
        Puntuacione puntuacion = new Puntuacione(); //Creamos variable para luego añadir


        Bundle recieved= getArguments();
        String user = recieved.getString("username");
        puntuacion.setUsername(user);
        puntuacion.setScore( (double) movimientos);

        PostPuntuacion pp = new PostPuntuacion();
        pp.setPuntuacion(puntuacion);

        APIService apiService = ApiUtils.getAPIService();
        Call<PostPuntuacion> callPost = apiService.createPuntuacion(pp);

        callPost.enqueue(new Callback<PostPuntuacion>() {
            @Override
            public void onResponse(Call<PostPuntuacion> call, Response<PostPuntuacion> response) {

            }

            @Override
            public void onFailure(Call<PostPuntuacion> call, Throwable t) {

            }
        });

         */

    }

    private void salvar_puntuacion(final User u){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute (Realm realm) {
                u.setRecord(movimientos);
                u.setRecord_time(tiempo);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
                u.setFecha_record(date);
            }
        });

    }

    private void landscape() {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Bundle b = new Bundle();
            MyDialog m = new MyDialog(); //para el dialog informativo
            b.putString("texto", "Please to play the game, don't put the device in landscape");
            m.setArguments(b);
            m.show(getFragmentManager(),"hola"); //show del dialog
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int cast = item.getItemId();
            if (cast == R.id.start) {
                if (stopped) {
                    stopped = false;
                    click_enabled = true;
                }
                else if (!started_game) {
                    started_game = true;
                    Toast.makeText(getActivity(), "Game started", Toast.LENGTH_SHORT).show();
                    bc = new BackgroundCount();
                    bc.execute();
                }
                return true;
            }
            else if (cast == R.id.stop) {
                stopped = true;
                click_enabled = false;
                return true;
            }
            else return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bc!=null) bc.cancel(true);
    }


    private class BackgroundCount extends AsyncTask<Integer,String,Integer> {

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            tiempo = integer-1;

            Bundle b = new Bundle();
            MyDialog m = new MyDialog(); //para el dialog informativo

            Realm.init(getContext());
            realm = Realm.getDefaultInstance();

            User u = realm.where(User.class).equalTo("username", user).findFirst();
            if (movimientos<u.getRecord() || (u.getRecord()==movimientos && tiempo<u.getRecord_time())) {
                b.putString("texto", "New record!! Added in Ranking. Game restarted");
                salvar_puntuacion(u);
            }
            else b.putString("texto", "Try again! " + String.valueOf(movimientos) + " moves in " + String.valueOf(tiempo) + "s");

            m.setArguments(b);
            m.show(getFragmentManager(),"hola"); //show del dialog

            Fragment f = new FragmentGame();
            Bundle aux = new Bundle();
            aux.putString("username", user);
            f.setArguments(aux);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container,f);
            ft.commit();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView.setText(values[0]);
        }


        @Override
        protected Integer doInBackground(Integer... integers) {
            int i = 0;
            while(started_game) {
                if(isCancelled()) break;
                String aux = String.valueOf(i);
                publishProgress("Time: " + aux);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!stopped) ++i;
            }
            return i;
        }
    }


}