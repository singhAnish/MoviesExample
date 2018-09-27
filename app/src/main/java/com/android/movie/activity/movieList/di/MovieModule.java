package com.android.movie.activity.movieList.di;

import com.android.movie.activity.movieList.MovieActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieModule {

    private final MovieActivity mActivity;

    public MovieModule(MovieActivity activity){
        this.mActivity = activity;
    }

    @Provides
    @MovieScope
    public MovieActivity movieActivity(){
        return mActivity;
    }

}
