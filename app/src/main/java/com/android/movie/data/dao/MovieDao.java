package com.android.movie.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.movie.model.movieList.MovieResults;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM MovieResults")
    List<MovieResults> getMovieData();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMovieData(MovieResults... model);

    @Query("DELETE FROM MovieResults")
    void clearMovieData();
}
