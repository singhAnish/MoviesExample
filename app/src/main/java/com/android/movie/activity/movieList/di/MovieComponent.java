package com.android.movie.activity.movieList.di;

import com.android.movie.activity.movieList.MovieActivity;
import com.android.movie.app.di.AppComponent;

import dagger.Component;

@Component(modules = MovieModule.class, dependencies = AppComponent.class) //dependencies annotation include extra dependencies
@MovieScope
public interface MovieComponent {
    void inject(MovieActivity activity);
}
