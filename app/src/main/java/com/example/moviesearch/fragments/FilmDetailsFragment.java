package com.example.moviesearch.fragments;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.bumptech.glide.Glide;
import com.example.moviesearch.App;
import com.example.moviesearch.R;
import com.example.moviesearch.database.DatabaseAction;
import com.example.moviesearch.model.Movie;

import io.reactivex.disposables.Disposable;

public class FilmDetailsFragment extends Fragment {
    private static final String ARG_MOVIE = "ARG_MOVIE";
    private static final String ARG_PARENT = "ARG_PARENT";

    private Movie movie;
    private int parent;
    private Disposable disposable;

    public static FilmDetailsFragment getInstance(Movie movie, int parent){
        Bundle bundle = new Bundle();
        FilmDetailsFragment fragment = new FilmDetailsFragment();
        bundle.putInt(ARG_PARENT, parent);
        bundle.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) return;

        movie = (Movie)getArguments().getSerializable(ARG_MOVIE);
        parent = getArguments().getInt(ARG_PARENT);

        fillWithData(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void fillWithData(View v) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity != null) {
            activity.getSupportActionBar().setTitle(movie.getTitle());
        }

        ImageView poster = v.findViewById(R.id.details_screen_poster);
        Glide.with(getActivity())
                .load(App.ING_URL_PREFIX + movie.getPoster())
                .into(poster);
        ((TextView) (v.findViewById(R.id.details_screen_release))).setText(movie.getReleaseDate());
        ((TextView) (v.findViewById(R.id.details_screen_rating))).setText(String.format("%s/10", movie.getPopularity().toString()));
        ((TextView) (v.findViewById(R.id.details_screen_overview))).setText(movie.getOverview());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (parent == App.PARENT_SEARCH) {
            inflater.inflate(R.menu.menu_details_from_search, menu);
        }
        if (parent == App.PARENT_MAIN) {
            inflater.inflate(R.menu.menu_details_from_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_movie_button:
                disposable = DatabaseAction.saveMovie(getContext(), movie);

                item.setTitle("Добавлено");
                item.setEnabled(false);
                return true;

            case R.id.delete_movie_button:
                FragmentManager fm = getFragmentManager();
                disposable = DatabaseAction.deleteMovie(getContext(), movie, fm);

                item.setTitle("Удалено");
                item.setEnabled(false);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }

}
