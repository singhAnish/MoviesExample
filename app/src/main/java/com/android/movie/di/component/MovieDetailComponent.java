package com.android.movie.di.component;

import com.android.movie.activity.MovieDetailActivity;
import com.android.movie.di.module.MovieDetailModule;
import com.android.movie.di.scoping.MovieDetailScope;

import dagger.Component;

@Component(modules = MovieDetailModule.class, dependencies = AppComponent.class) //dependencies annotation include extra dependencies
@MovieDetailScope
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);
}
