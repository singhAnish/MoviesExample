package com.android.movie.contract;


import com.android.movie.model.movieDetail.MovieDetail;
import com.android.movie.model.movieList.MovieResults;

import retrofit2.Response;

public interface MovieDetailContract {


    interface View { //View Interface for MovieActivity

        void init();

        void fetchFromList(MovieResults data);

        void movieDetailApiCall(int MovieID);

        void loadData(Response<MovieDetail> response);

    }


    interface Presenter{ //Presenter Interface for MovieActivity

        void initVariables();

        void fetchDataFromList(MovieResults data);

        void movieDetailApiCall(int MovieID);

        void loadData(Response<MovieDetail> response);
    }
}

