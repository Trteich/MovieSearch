package com.example.moviesearch.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesearch.model.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    Single<List<Movie>> getAll();

    @Query("SELECT * FROM movie WHERE id=:id")
    Single<Movie> findById(Long id);

    @Insert
    Long insert(Movie movie);

    @Delete
    int delete(Movie movie);
}
