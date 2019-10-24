package com.example.moviesearch;

import android.app.Application;

import androidx.room.Room;

import com.example.moviesearch.api.Deserialiser;

import com.example.moviesearch.api.RetrofitClient;
import com.example.moviesearch.api.ThemovieDBapi;
import com.example.moviesearch.database.Database;
import com.example.moviesearch.model.Movie;

import retrofit2.Retrofit;

public class App extends Application {
    public final static String ING_URL_PREFIX = "https://image.tmdb.org/t/p/w342";
    public final static String API_KEY = "458fd43eb9a1e8c988f7f0dfb1f50ec3";
    public final static String LANG = "ru";
    public final static int PARENT_SEARCH = -1;
    public final static int PARENT_MAIN = 1;

    private static ThemovieDBapi themovieDBapi;
    private static Database db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(this, Database.class, "database")
                .build();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(Movie.class, new Deserialiser());

        themovieDBapi = retrofit.create(ThemovieDBapi.class);


    }

    public static ThemovieDBapi getThemovieDBapi() {
        return themovieDBapi;
    }

    public static Database getDb() {
        return db;
    }
}
