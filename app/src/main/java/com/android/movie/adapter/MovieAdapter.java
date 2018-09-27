package com.android.movie.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.movie.BuildConfig;
import com.android.movie.R;
import com.android.movie.databinding.ListMovieBinding;
import com.android.movie.model.movieList.MovieResults;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<MovieResults> mModel;
    private RequestManager mManager;
    private RequestOptions option;

    public MovieAdapter(RequestManager manager, ArrayList<MovieResults> model) {
        this.mManager = manager;
        this.mModel  = model;
        option = new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    public void updateModel(ArrayList<MovieResults> model) {
        this.mModel  = model;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_movie, parent, false);
        return new MovieAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder itemHolder, int position) {
        MyViewHolder holder = (MyViewHolder) itemHolder;
        final MovieResults model = mModel.get(position);

        holder.mBinding.movieName.setText(model.getTitle());
        holder.mBinding.movieYear.setText(model.getRelease_date().split("-")[0]);

        String rating = Double.toString(model.getVote_average());
        holder.mBinding.movieRating1.setText(rating.substring(0, 1));
        holder.mBinding.movieRating2.setText(rating.substring(1));

        mManager.load(BuildConfig.IMG_URL.concat(model.getPoster_path())).thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade()).apply(option).into(holder.mBinding.movieImage);
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ListMovieBinding mBinding;
        private MyViewHolder(ListMovieBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}


