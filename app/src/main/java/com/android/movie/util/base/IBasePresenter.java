package com.android.movie.util.base;


public interface IBasePresenter<ViewT> {

    void onViewActive(ViewT view);

    void onViewInactive();
}
