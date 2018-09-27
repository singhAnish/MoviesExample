package com.android.movie.app.di;

import com.android.movie.data.AppDatabase;
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
