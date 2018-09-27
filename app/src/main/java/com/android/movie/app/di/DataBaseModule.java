package com.android.movie.app.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.android.movie.data.AppDatabase;

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
