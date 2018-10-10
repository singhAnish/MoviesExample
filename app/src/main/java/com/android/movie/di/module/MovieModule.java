package com.android.movie.di.module;

import com.android.movie.activity.MovieActivity;
import com.android.movie.di.scoping.MovieScope;

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
