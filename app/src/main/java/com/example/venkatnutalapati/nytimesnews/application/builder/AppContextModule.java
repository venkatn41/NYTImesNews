package com.example.venkatnutalapati.nytimesnews.application.builder;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by venkat on 2/3/18.
 */

@Module
public class AppContextModule {

    private final Context context;

    public AppContextModule(Context aContext) {
        this.context = aContext;
    }

    @Provides
    Context provideAppContext() {
        return context;
    }

}
