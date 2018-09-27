package com.android.movie.presenter;

import com.android.movie.contract.MovieContract.Presenter;
import com.android.movie.contract.MovieContract.View;
import com.android.movie.model.movieList.Movie;
import com.android.movie.util.base.BasePresenter;

import retrofit2.Response;

public class MoviePresenter extends BasePresenter<View> implements Presenter {

    public MoviePresenter(View view) {
        this.view = view;
    }

    @Override
    public void initVariables() {
        view.init();
    }

    @Override
    public void internetAvailable() {
        view.checkInternet();
    }

    @Override
    public void loadData() {
        view.loadContentFromAPI();
    }

    @Override
    public void updateApiSuccess(Response<Movie> response) {
        if(response != null){
            view.updateSuccess(response);
        }
    }

    @Override
    public void loadGridView() {
        view.updateGridView();
    }

    @Override
    public void showFooter(boolean footerBool, boolean isInternet) {
        view.footerView(footerBool, isInternet);
    }

    @Override
    public void recyclerClicked(android.view.View v, int pos) {
        view.openMovieDetail(v, pos);
    }
}
