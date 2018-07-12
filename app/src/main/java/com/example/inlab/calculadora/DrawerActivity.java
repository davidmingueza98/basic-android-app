package com.example.inlab.calculadora;

import android.content.Intent;
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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inlab.calculadora.Retrofit.APIService;
import com.example.inlab.calculadora.Retrofit.ApiUtils;
import com.example.inlab.calculadora.Retrofit.GetPuntuacion;
import com.example.inlab.calculadora.Retrofit.PostPuntuacion;
import com.example.inlab.calculadora.Retrofit.Puntuacione;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        APIService apiService = ApiUtils.getAPIService();
        Call<GetPuntuacion> call = apiService.getPuntuaciones();

        Puntuacione puntuacion = new Puntuacione(); //Creamos variable para luego añadir
        puntuacion.setUsername("Alvaro");
        puntuacion.setScore(99999.0);

        PostPuntuacion pp = new PostPuntuacion();
        pp.setPuntuacion(puntuacion);

        /**
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment f = new FragmentCalculadora();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container,f);
        ft.commit();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        TextView textViewUser = navigationView.getHeaderView(0).findViewById(R.id.textViewUser);
        textViewUser.setText(user);

    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment f = null;
        navigationView.setCheckedItem(id);
        if (id == R.id.nav_gallery) f = new FragmentCalculadora();
        if (id == R.id.nav_logout) {
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
        return true;
    }
}
