package com.example.moviesearch.api;

import com.example.moviesearch.model.ModelMovies;
import com.example.moviesearch.model.ModelPerson;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThemovieDBapi {

    @GET("/3/search/multi")
    Single<Response<ModelMovies>> getMovies(@Query("api_key") String apiKey,
                                            @Query("language") String lang,
                                            @Query("query") String videoName);


    @GET("/3/search/person")
    Single<Response<ModelPerson>> getPersonMovies(@Query("api_key") String apiKey,
                                                  @Query("language") String lang,
                                                  @Query("query") String personName);
}
