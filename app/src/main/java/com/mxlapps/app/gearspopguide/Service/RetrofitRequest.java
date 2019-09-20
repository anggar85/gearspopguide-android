package com.mxlapps.app.gearspopguide.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mxlapps.app.gearspopguide.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    private static Retrofit retrofit;
    public static final String URL_SERVER_DOCDIGITALES = BuildConfig.API_BASE_URL;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            //Agregando interceptor para logs de retrofit
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            if (BuildConfig.APP_IS_IN_PRODUCTION){
                retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(URL_SERVER_DOCDIGITALES)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }else{
                retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(URL_SERVER_DOCDIGITALES)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();
            }
        }
        return retrofit;
    }
}


