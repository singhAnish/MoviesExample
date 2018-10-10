package com.android.movie;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.android.movie.di.component.AppComponent;
import com.android.movie.di.component.DaggerAppComponent;
import com.android.movie.di.module.app.ContextModule;
import com.android.movie.util.ConnectionDetector;

import timber.log.Timber;

public class MyApp extends Application {

    private static MyApp mInstance;
    private AppComponent component;

    public static MyApp get(Activity activity) {
        return (MyApp) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        this.registerReceiver(ConnectionDetector.getDetector().new NetworkReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        ConnectionDetector.getDetector().initConnectionState(this);

        Timber.plant(new Timber.DebugTree());
        component = DaggerAppComponent.builder().contextModule(new ContextModule(this)).build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = null;
        component = null;
    }

    public static Context getContext(){
        return mInstance;
    }

    public AppComponent getComponent() {
        return component;
    }
}