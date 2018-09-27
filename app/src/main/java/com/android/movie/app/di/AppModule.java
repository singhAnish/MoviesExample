package com.android.movie.app.di;

import com.android.movie.BuildConfig;
import com.android.movie.network.DateTimeConverter;
import com.android.movie.network.MovieRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module(includes = NetworkModule.class) //include annotation will provide corresponding dependencies
public class AppModule {

    @Provides @AppScope
    public MovieRepo getMovieRepo(Retrofit mRetrofit){
        return mRetrofit.create(MovieRepo.class);
    }

    @Provides @AppScope
    public Retrofit getRetrofit(OkHttpClient mOKHttpClient, Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOKHttpClient)
                .baseUrl(BuildConfig.BASE_URL).build();
    }

    @Provides @AppScope
    public Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.registerTypeAdapter(DateTime.class, new DateTimeConverter()).create();
    }

}
