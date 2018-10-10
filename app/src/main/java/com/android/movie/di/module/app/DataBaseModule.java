package com.android.movie.di.module.app;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.android.movie.data.AppDatabase;
import com.android.movie.di.qualifier.AppContext;
import com.android.movie.di.scoping.AppScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class DataBaseModule {

    @Provides
    @AppScope
    public AppDatabase requestDataBase(@AppContext Context context){
        return  Room.databaseBuilder(context, AppDatabase.class, "Development").allowMainThreadQueries().build();
    }
}
