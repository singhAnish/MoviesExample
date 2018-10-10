package com.android.movie.di.module.app;

import android.content.Context;

import com.android.movie.di.qualifier.AppContext;
import com.android.movie.di.scoping.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Context context){
        this.mContext = context;
    }

    @Provides
    @AppScope
    @AppContext //can also use Named annotation to differentiate between context
    public Context provideContext(){
        return  mContext;
    }

}
