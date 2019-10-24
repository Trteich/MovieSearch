package com.example.moviesearch.database;


import androidx.room.RoomDatabase;
import com.example.moviesearch.model.Movie;

@androidx.room.Database(entities = {Movie.class},version = 1)
public abstract class Database extends RoomDatabase {
    public abstract MovieDao movieDao();
}
