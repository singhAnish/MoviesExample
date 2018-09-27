package com.android.movie.activity.movieDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.movie.BuildConfig;
import com.android.movie.R;
import com.android.movie.activity.movieDetail.di.DaggerMovieDetailComponent;
import com.android.movie.activity.movieDetail.di.MovieDetailComponent;
import com.android.movie.activity.movieDetail.di.MovieDetailModule;
import com.android.movie.app.MyApp;
import com.android.movie.contract.MovieDetailContract.View;
import com.android.movie.databinding.ActivityMovieDetailBinding;
import com.android.movie.model.movieDetail.MovieDetail;
import com.android.movie.model.movieList.MovieResults;
import com.android.movie.network.MovieRepo;
import com.android.movie.presenter.MovieDetailPresenter;
import com.android.movie.util.ConnectionDetector;
import com.android.movie.util.Local;
import com.android.movie.util.base.BaseActivity;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends BaseActivity implements View {

    private MovieDetailPresenter mPresenter;
    private ActivityMovieDetailBinding mBinding;

    @Inject RequestManager mRequestManager;
    @Inject MovieRepo mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        MovieDetailComponent component = DaggerMovieDetailComponent.builder().movieDetailModule(new MovieDetailModule(this)).appComponent(MyApp.get(this).getComponent()).build();
        component.inject(this);

        mPresenter = new MovieDetailPresenter(this);
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
        mBinding.backBtn.setOnClickListener(v -> onBackPressed());
        mBinding.detailWatchNow.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));
        mBinding.playBtn.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));
        mBinding.bookmarkBtn.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));
        mBinding.bookmarkedBtn.setOnClickListener(v -> showToast(R.string.functionalityNotAdded));

        MovieResults data = getIntent().getParcelableExtra("movieResults");
        mPresenter.fetchDataFromList(data);
    }

    @Override
    public void fetchFromList(MovieResults data){
        mBinding.movieDetailsLay.setVisibility(android.view.View.GONE);
        mBinding.detailProgressBar.setVisibility(android.view.View.VISIBLE);
        mBinding.movieDetailName.setText(data.getTitle());
        mBinding.detailRatingCount.setText("(".concat(String.valueOf(data.getVote_count())).concat(")"));

        String rating = Double.toString(data.getVote_average());
        mBinding.movieDetailRating1.setText(rating.substring(0, 1));
        mBinding.movieDetailRating2.setText(rating.substring(1));

        mRequestManager.load(BuildConfig.IMG_URL.concat(data.getPoster_path())).thumbnail(0.05f).into(mBinding.movieDetailImage);

        if (ConnectionDetector.isConnected()) {
            mPresenter.movieDetailApiCall(data.getId());
        } else {
            finish();
        }
    }

    @Override
    public void movieDetailApiCall(int movieID) {
        Call<MovieDetail> call = mRepo.getMovieDetail(movieID, BuildConfig.KEY);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                mPresenter.loadData(response);
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadData(Response<MovieDetail> response){
        if (response.isSuccessful()) {
            mBinding.detailProgressBar.setVisibility(android.view.View.GONE);
            mBinding.movieDetailsLay.setVisibility(android.view.View.VISIBLE);

            MovieDetail detail = response.body();
            mRequestManager.load(BuildConfig.IMG_URL.concat(detail.getBackdrop_path())).thumbnail(0.05f).into(mBinding.moviePoster);

            StringBuilder builder = new StringBuilder();
            if(!detail.getRelease_date().trim().isEmpty() && detail.getRuntime() > 0){
                builder.append(detail.getRelease_date().split("-")[0]).append("\t\t")
                        .append(String.valueOf(detail.getRuntime())).append("m");
            }else if(detail.getRuntime() > 0){
                builder.append(String.valueOf(detail.getRuntime())).append("m");
            }else{
                builder.append(detail.getRelease_date().split("-")[0]);
            }
            mBinding.detailRelaseTime.setText(builder.toString());

            if(!detail.getTagline().trim().isEmpty()){
                mBinding.detailTagLine.setVisibility(android.view.View.VISIBLE);
                mBinding.detailTagLine.setText(detail.getTagline());
            }else{
                mBinding.detailTagLine.setVisibility(android.view.View.GONE);
            }

            if(!detail.getOverview().trim().isEmpty()) {
                mBinding.detailDescription.setVisibility(android.view.View.VISIBLE);
                mBinding.detailDescription.setText(detail.getOverview());
            }else{
                mBinding.detailDescription.setVisibility(android.view.View.GONE);
            }

            StringBuilder studio = new StringBuilder();
            for(int i = 0; i< detail.getProduction_companies().length; i++){
                studio.append(detail.getProduction_companies()[i].getName());
                if(i != detail.getProduction_companies().length - 1){
                    studio.append(", ");
                }
            }
            mBinding.detailStudio.setText(studio.toString());

            StringBuilder genre = new StringBuilder();
            for(int i = 0; i< detail.getGenres().length; i++){
                genre.append(detail.getGenres()[i].getName());
                if(i != detail.getGenres().length -1){
                    genre.append(", ");
                }
            }
            mBinding.detailGenre.setText(genre.toString());

            StringBuilder language = new StringBuilder();
            for(int i = 0; i< detail.getSpoken_languages().length; i++){
                language.append(detail.getSpoken_languages()[i].getName());
                if(i != detail.getSpoken_languages().length -1){
                    language.append(", ");
                }
            }
            mBinding.detailLanguage.setText(language.toString());
            mBinding.detailReleased.setText(detail.getRelease_date());
            mBinding.detailRuntime.setText(Local.getDurationHourMin(detail.getRuntime()));
        }
    }
}
