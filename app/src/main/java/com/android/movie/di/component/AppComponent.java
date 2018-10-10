package com.android.movie.di.component;

import com.android.movie.data.AppDatabase;
import com.android.movie.di.module.app.AppModule;
import com.android.movie.di.module.app.DataBaseModule;
import com.android.movie.di.module.app.GlideModule;
import com.android.movie.di.scoping.AppScope;
import com.android.movie.network.MovieRepo;
import com.bumptech.glide.RequestManager;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, GlideModule.class, DataBaseModule.class})
public interface AppComponent {

    MovieRepo getMovieRepo(); // will return instance of APIService

    RequestManager getGlideManager(); // Will return Glide RequestManager

    AppDatabase getDataBase(); // Will return instance of AppDataBase
}
