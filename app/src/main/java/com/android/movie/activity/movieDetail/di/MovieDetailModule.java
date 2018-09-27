package com.android.movie.activity.movieDetail.di;

import com.android.movie.activity.movieDetail.MovieDetailActivity;

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
