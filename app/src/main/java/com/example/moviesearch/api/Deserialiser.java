package com.example.moviesearch.api;

import com.example.moviesearch.model.Movie;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Deserialiser implements JsonDeserializer<Movie> {

    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movie movie = new Movie();
        if (json != null) {
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.get("media_type").getAsString().equals("movie")) {
                movie.setId(jsonObject.get("id").getAsLong());

                if (jsonObject.get("vote_average") != null && !jsonObject.get("vote_average").isJsonNull())
                    movie.setPopularity(jsonObject.get("vote_average").getAsDouble());

                if (jsonObject.get("poster_path") != null && !jsonObject.get("poster_path").isJsonNull())
                    movie.setPoster(jsonObject.get("poster_path").getAsString());

                if (jsonObject.get("title") != null && !jsonObject.get("title").isJsonNull())
                    movie.setTitle(jsonObject.get("title").getAsString());

                if (jsonObject.get("release_date") != null && !jsonObject.get("release_date").isJsonNull())
                    movie.setReleaseDate(jsonObject.get("release_date").getAsString());

                if (jsonObject.get("overview") != null && !jsonObject.get("overview").isJsonNull())
                    movie.setOverview(jsonObject.get("overview").getAsString());
            }


            if (jsonObject.get("media_type").getAsString().equals("tv")) {
                movie.setId(jsonObject.get("id").getAsLong());

                if (jsonObject.get("vote_average") != null && !jsonObject.get("vote_average").isJsonNull())
                    movie.setPopularity(jsonObject.get("vote_average").getAsDouble());
                if (jsonObject.get("poster_path") != null && !jsonObject.get("poster_path").isJsonNull())
                    movie.setPoster(jsonObject.get("poster_path").getAsString());

                if (jsonObject.get("name") != null && !jsonObject.get("name").isJsonNull())
                    movie.setTitle(jsonObject.get("name").getAsString());

                if (jsonObject.get("first_air_date") != null && !jsonObject.get("first_air_date").isJsonNull())
                    movie.setReleaseDate(jsonObject.get("first_air_date").getAsString());

                if (jsonObject.get("overview") != null && !jsonObject.get("overview").isJsonNull())
                    movie.setOverview(jsonObject.get("overview").getAsString());
            }
        }
        return movie;
    }
}