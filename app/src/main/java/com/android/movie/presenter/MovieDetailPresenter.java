package com.android.movie.presenter;

import com.android.movie.contract.MovieDetailContract.Presenter;
import com.android.movie.contract.MovieDetailContract.View;
import com.android.movie.model.movieDetail.MovieDetail;
import com.android.movie.model.movieList.MovieResults;
import com.android.movie.util.base.BasePresenter;

import retrofit2.Response;

public class MovieDetailPresenter extends BasePresenter<View> implements Presenter{

    public MovieDetailPresenter(View view){
        this.view = view;
    }

    @Override
    public void initVariables() {
        view.init();
    }

    @Override
    public void fetchDataFromList(MovieResults data) {
        view.fetchFromList(data);
    }

    @Override
    public void movieDetailApiCall(int movieID) {
        view.movieDetailApiCall(movieID);
    }

    @Override
    public void loadData(Response<MovieDetail> response) {
        if(response != null) {
            view.loadData(response);
        }
    }
}
