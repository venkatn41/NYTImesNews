package com.example.venkatnutalapati.nytimesnews.application.builder;

import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by venkat on 2/3/18.
 */

@Module
public class NYNewsApiServiceModule {

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";

    @Provides
    NYNewsApi provideApiService(OkHttpClient client, GsonConverterFactory gson, RxJava2CallAdapterFactory rxAdapter)
    {
        Retrofit retrofit =   new Retrofit.Builder().client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .build();

        return  retrofit.create(NYNewsApi.class);
    }
}