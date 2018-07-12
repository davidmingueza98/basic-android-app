package com.example.inlab.calculadora.Retrofit;

import com.example.inlab.calculadora.Puntuacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @POST("/puntuaciones")
    Call<PostPuntuacion> createPuntuacion(@Body PostPuntuacion puntuacion);

    @GET("/puntuaciones")
    Call<GetPuntuacion> getPuntuaciones();
}