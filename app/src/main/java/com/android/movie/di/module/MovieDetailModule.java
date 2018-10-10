package com.android.movie.di.module;

import com.android.movie.activity.MovieDetailActivity;
import com.android.movie.di.scoping.MovieDetailScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailModule {

    private final MovieDetailActivity mActivity;

    public MovieDetailModule(MovieDetailActivity activity){
        this.mActivity = activity;
    }

    @Provides @MovieDetailScope
    public MovieDetailActivity movieDetailActivity(){
        return mActivity;
    }

}
