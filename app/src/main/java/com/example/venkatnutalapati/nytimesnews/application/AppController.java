package com.example.venkatnutalapati.nytimesnews.application;

import android.app.Application;

import com.example.venkatnutalapati.nytimesnews.application.builder.AppComponent;
import com.example.venkatnutalapati.nytimesnews.application.builder.AppContextModule;
import com.example.venkatnutalapati.nytimesnews.application.builder.DaggerAppComponent;


/**
 * Created by venkat on 2/3/18.
 */

public class AppController extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appContextModule(new AppContextModule(this)).build();
    }

    public static AppComponent getNetComponent() {
        return appComponent;
    }
}
