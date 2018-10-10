package com.android.movie.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.movie.util.Local;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private ProgressDialog mDialog;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getContext());
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage("Loading...");
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showToast(int id) {
        Local.toastMessage(id);
    }

    @Override
    public void showToast(String message) {
        Local.toastStringMessage(message);
    }
}
