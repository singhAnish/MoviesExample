package com.android.movie.activity.movieDetail.di;

import com.android.movie.activity.movieDetail.MovieDetailActivity;
import com.android.movie.app.di.AppComponent;

import dagger.Component;

@Component(modules = MovieDetailModule.class, dependencies = AppComponent.class) //dependencies annotation include extra dependencies
@MovieDetailScope
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);
}
