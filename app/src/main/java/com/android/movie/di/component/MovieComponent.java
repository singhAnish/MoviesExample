package com.android.movie.di.component;

import com.android.movie.activity.MovieActivity;
import com.android.movie.di.module.MovieModule;
import com.android.movie.di.scoping.MovieScope;

import dagger.Component;

@Component(modules = MovieModule.class, dependencies = AppComponent.class) //dependencies annotation include extra dependencies
@MovieScope
public interface MovieComponent {
    void inject(MovieActivity activity);
}
