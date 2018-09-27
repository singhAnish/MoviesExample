package com.android.movie.contract;

import com.android.movie.model.movieList.Movie;

import retrofit2.Response;

public interface MovieContract {


    interface View { //View Interface for MovieActivity

        void init();

        void checkInternet();

        void loadContentFromAPI();

        void updateSuccess(Response<Movie> response);

        void updateGridView();

        void footerView(boolean footerBool, boolean isInternet);

        void openMovieDetail(android.view.View view, int pos);
    }


    interface Presenter{ //Presenter Interface for MovieActivity

        void initVariables();

        void internetAvailable();

        void loadData();

        void updateApiSuccess(Response<Movie> response);

        void loadGridView();

        void showFooter(boolean footerBool, boolean isInternet);

        void recyclerClicked(android.view.View view, int pos);
    }
}
