package com.example.inlab.calculadora.Retrofit;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://178.128.166.139/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}