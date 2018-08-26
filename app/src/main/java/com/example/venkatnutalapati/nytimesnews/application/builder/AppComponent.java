package com.example.venkatnutalapati.nytimesnews.application.builder;

import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi;

import dagger.Component;
/**
 * Created by venkat on 2/3/18.
 */

@Component(modules = {NetworkModule.class, AppContextModule.class, NYNewsApiServiceModule.class})
public interface AppComponent {
    NYNewsApi apiService();
}
