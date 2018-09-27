package com.android.movie.activity.movieList;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.android.movie.BuildConfig;
import com.android.movie.R;
import com.android.movie.activity.movieDetail.MovieDetailActivity;
import com.android.movie.activity.movieList.di.DaggerMovieComponent;
import com.android.movie.activity.movieList.di.MovieComponent;
import com.android.movie.activity.movieList.di.MovieModule;
import com.android.movie.adapter.MovieAdapter;
import com.android.movie.app.MyApp;
import com.android.movie.contract.MovieContract.View;
import com.android.movie.databinding.ActivityMovieListBinding;
import com.android.movie.model.movieList.Movie;
import com.android.movie.model.movieList.MovieResults;
import com.android.movie.network.MovieRepo;
import com.android.movie.presenter.MoviePresenter;
import com.android.movie.util.ConnectionDetector;
import com.android.movie.util.Local;
import com.android.movie.util.RecyclerItemClickListener;
import com.android.movie.util.base.BaseActivity;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends BaseActivity implements View {

    private boolean loading = false;
    private GridLayoutManager layoutManager;
    private int pageNumber = 1, totalNumberOfPages = 0;
    private MovieAdapter mAdapter;
    private ArrayList<MovieResults> mModel;
    private MoviePresenter mPresenter;
    private ActivityMovieListBinding mBinding;

    @Inject MovieRepo mRepo;
    @Inject MovieActivity mActivity;
    @Inject RequestManager mRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);

        MovieComponent component = DaggerMovieComponent.builder().movieModule(new MovieModule( this)).appComponent(MyApp.get(this).getComponent()).build();
        component.inject(this);

        mPresenter = new MoviePresenter(this);
        mPresenter.initVariables();
    }

    /*@Override
    public void onPause() {
        super.onPause();
        mPresenter.onViewInactive();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onViewActive(this);
    }*/

    @Override
    public void init() {
        mBinding.retryBtn.setOnClickListener(v -> mPresenter.internetAvailable());
        mBinding.footerRetryButton.setOnClickListener(v -> {
            if (ConnectionDetector.isConnected()) {
                mPresenter.loadData();
            }
        });
        mBinding.menuBtn.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));
        mBinding.moreBtn.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));
        mBinding.searchBtn.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));

        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + 1) && (pageNumber <= totalNumberOfPages)) {
                    if (ConnectionDetector.isConnected()) {
                        mPresenter.showFooter(true, true);
                        mPresenter.loadData();
                        loading = true;
                    } else {
                        mPresenter.showFooter(true, false);
                        loading = false;
                    }
                }
            }
        });

        mBinding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, (view, position) -> mPresenter.recyclerClicked(view, position)));
        mPresenter.internetAvailable();
    }

    @Override
    public void openMovieDetail(android.view.View view, int pos){
        Intent intent = new Intent(mActivity, MovieDetailActivity.class);
        intent.putExtra("movieResults",  mModel.get(pos));
        Pair imageAnim = Pair.create(view.findViewById(R.id.movieImage), getResources().getString(R.string.imageTransition));
        Pair nameAnim = Pair.create(view.findViewById(R.id.movieName), getResources().getString(R.string.nameTransition));
        Pair ratingAnim = Pair.create(view.findViewById(R.id.ratingLay), getResources().getString(R.string.ratingTransition));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, imageAnim, nameAnim, ratingAnim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void checkInternet() {
        if (pageNumber == 1 && ConnectionDetector.isConnected()) {
            mBinding.internetDisabled.setVisibility(android.view.View.GONE);
            mBinding.internetEnabled.setVisibility(android.view.View.VISIBLE);
            showDialog();
            mPresenter.loadData();
        }else {
            mBinding.internetEnabled.setVisibility(android.view.View.GONE);
            mBinding.internetDisabled.setVisibility(android.view.View.VISIBLE);
        }
    }

    @Override
    public void loadContentFromAPI() {
        if (ConnectionDetector.isConnected()) {
            Call<Movie> call = mRepo.getMovieListData(BuildConfig.KEY, pageNumber);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                    Local.logMessage("URL Success : "+call.request().url());
                    if(pageNumber == 1){
                        mBinding.recyclerView.setVisibility(android.view.View.VISIBLE);
                    }
                    mPresenter.updateApiSuccess(response);
                }

                @Override
                public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                    Local.logMessage("URL Fail : "+call.request().url());
                    if(pageNumber == 1) {
                        dismissDialog();
                    }
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void updateSuccess(Response<Movie> response){
        if (response.isSuccessful()) {
            Movie movie = response.body();
            if(pageNumber == 1) {
                dismissDialog();
                if(movie != null){
                    totalNumberOfPages = movie.getTotal_pages();
                }
            }

            if (movie != null && movie.getResults().length > 0) {
                if(mModel == null){
                    mModel = new ArrayList<>();
                }
                mModel.addAll(new ArrayList<>(Arrays.asList(movie.getResults())));
                pageNumber++;
                mPresenter.loadGridView();
            } else {
                showToast(R.string.somethingWrong);
            }
        } else {
            showToast(R.string.somethingWrong);
        }
    }

    @Override
    public void updateGridView() {
        if (layoutManager == null) {
            layoutManager = new GridLayoutManager(mActivity, 2);
        }
        if (mBinding.recyclerView.getAdapter() == null) {
            mBinding.recyclerView.setLayoutManager(layoutManager);
            mAdapter = new MovieAdapter(mRequestManager, mModel);
            mBinding.recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateModel(mModel);
        }
        mPresenter.showFooter(false, false);
        loading = false;
    }

    @Override
    public void footerView(boolean footerBool, boolean isInternet) {
        if (footerBool) {
            mBinding.progressLay.setVisibility(android.view.View.VISIBLE);
            if (isInternet) {
                mBinding.footerNoInternet.setVisibility(android.view.View.GONE);
                mBinding.loadingLay.setVisibility(android.view.View.VISIBLE);
            } else {
                mBinding.loadingLay.setVisibility(android.view.View.GONE);
                mBinding.footerNoInternet.setVisibility(android.view.View.VISIBLE);
            }
        } else {
            mBinding.progressLay.setVisibility(android.view.View.GONE);
        }
    }
}
