package com.example.inlab.calculadora;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inlab.calculadora.Retrofit.APIService;
import com.example.inlab.calculadora.Retrofit.ApiUtils;
import com.example.inlab.calculadora.Retrofit.GetPuntuacion;
import com.example.inlab.calculadora.Retrofit.PostPuntuacion;
import com.example.inlab.calculadora.Retrofit.Puntuacione;

import retrofit2.Call;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    String user;
    Toolbar toolbar;

    Integer selected_item = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        /**
        APIService apiService = ApiUtils.getAPIService();
        Call<GetPuntuacion> call = apiService.getPuntuaciones();

        Puntuacione puntuacion = new Puntuacione(); //Creamos variable para luego añadir
        puntuacion.setUsername("Alvaro");
        puntuacion.setScore(99999.0);

        PostPuntuacion pp = new PostPuntuacion();
        pp.setPuntuacion(puntuacion);


        Call<PostPuntuacion> callPost = apiService.createPuntuacion(pp);

        call.enqueue(new Callback<GetPuntuacion>() {
             @Override
             public void onResponse(Call<GetPuntuacion> call, Response<GetPuntuacion> response) {
                 response.body().getPuntuaciones();
             }

             @Override
             public void onFailure(Call<GetPuntuacion> call, Throwable t) {

             }
         });

        callPost.enqueue(new Callback<PostPuntuacion>() {
             @Override
             public void onResponse(Call<PostPuntuacion> call, Response<PostPuntuacion> response) {

             }

             @Override
             public void onFailure(Call<PostPuntuacion> call, Throwable t) {

             }
         });
         */

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cargar_calculadora_incial();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        TextView textViewUser = navigationView.getHeaderView(0).findViewById(R.id.textViewUser);
        textViewUser.setText(user); //Escribe el nombre del usuario

    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        selected_item = id;

        Fragment f = null;
        navigationView.setCheckedItem(id);
        if (id == R.id.nav_calculator) {
            toolbar.setTitle("Calculator");
            f = new FragmentCalculadora();
        }
        if (id == R.id.nav_game) {
            Toast.makeText(getApplicationContext(),"Do you want to play a game?", Toast.LENGTH_LONG).show();
            toolbar.setTitle("Game");

            Bundle b = new Bundle();
            b.putString("username", user);
            f = new FragmentGame();
            f.setArguments(b);
        }
        if (id == R.id.nav_ranking) {
            toolbar.setTitle("Ranking");
            f = new FragmentPuntuaciones();
        }
        if (id == R.id.nav_music) {
            toolbar.setTitle("Music");
            f = new FragmentMusic();
        }
        if (id == R.id.nav_logout) {
            elimina_shared_preferences();
            Toast.makeText(getApplicationContext(),"See you soon " + user, Toast.LENGTH_LONG).show();
            finish();
        }
        if(f!= null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, f);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(selected_item==R.id.nav_game) getMenuInflater().inflate(R.menu.menu, menu);
        else if (selected_item ==R.id.nav_calculator) getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("fragment", selected_item);
    }

    @Override
    public void onRestoreInstanceState(Bundle outstate){
        super.onRestoreInstanceState(outstate);
        selected_item = outstate.getInt("fragment");

        Fragment f = null;
        navigationView.setCheckedItem(selected_item);
        if (selected_item == R.id.nav_calculator) {
            toolbar.setTitle("Calculator");
            f = new FragmentCalculadora();
        }
        if (selected_item == R.id.nav_game) {
            Toast.makeText(getApplicationContext(),"Do you want to play a game?", Toast.LENGTH_LONG).show();
            toolbar.setTitle("Game");

            Bundle b = new Bundle();
            b.putString("username", user);
            f = new FragmentGame();
            f.setArguments(b);
        }
        if (selected_item == R.id.nav_ranking) {
            toolbar.setTitle("Ranking");
            f = new FragmentPuntuaciones();
        }
        if (selected_item == R.id.nav_music) {
            toolbar.setTitle("Music");
            f = new FragmentMusic();
        }
        if(f!= null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, f);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();

    }


    private void elimina_shared_preferences(){
        SharedPreferences check = getSharedPreferences("username",Context.MODE_PRIVATE);
        check.edit().clear().commit();
    }

    private void cargar_calculadora_incial(){
         selected_item = R.id.nav_calculator;
         navigationView.setCheckedItem(selected_item);
         toolbar.setTitle("Calculator");
         Fragment f = new FragmentCalculadora();
         FragmentManager fm = getSupportFragmentManager();
         FragmentTransaction ft = fm.beginTransaction();
         ft.replace(R.id.fragment_container,f);
         ft.commit();
    }


}

