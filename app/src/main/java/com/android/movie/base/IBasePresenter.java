package com.android.movie.base;


public interface IBasePresenter<V> {

    void onViewActive(V view);

    void onViewInactive();
}
