package com.example.moviesearch.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviesearch.fragments.FilmDetailsFragment;
import com.example.moviesearch.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Movie> movies = new ArrayList<>();
    private int parent;

    public MyPagerAdapter(@NonNull FragmentManager fm, List<Movie> movies, int parent) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.movies = movies;
        this.parent = parent;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FilmDetailsFragment.getInstance(movies.get(position), parent);
    }

    @Override
    public int getCount() {
        return movies.size();
    }
}
