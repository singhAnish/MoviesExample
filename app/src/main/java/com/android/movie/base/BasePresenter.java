package com.android.movie.base;

public class BasePresenter<V> implements IBasePresenter<V> {

    protected V view;

    @Override
    public void onViewActive(V view) {
        this.view = view;
    }

    @Override
    public void onViewInactive() {
        view = null;
    }
}
