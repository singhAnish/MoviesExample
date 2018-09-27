package com.android.movie.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.android.movie.data.dao.MovieDao;
import com.android.movie.model.movieList.MovieResults;

@Database(entities = {MovieResults.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

}


