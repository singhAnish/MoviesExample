package com.android.movie.network;

import com.android.movie.model.movieDetail.MovieDetail;
import com.android.movie.model.movieList.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieRepo {

    //https://api.themoviedb.org/3/trending/movie/week?api_key=YOUR_KEY&page=2
    @GET("trending/movie/week")
    Call<Movie> getMovieListData(@Query("api_key") String key, @Query("page") int page);


    //https://api.themoviedb.org/3/movie/MOVIE_ID?api_key=YOUR_KEY
    @GET("movie/{movieID}")
    Call<MovieDetail> getMovieDetail(@Path("movieID") int id, @Query("api_key") String key);


}
