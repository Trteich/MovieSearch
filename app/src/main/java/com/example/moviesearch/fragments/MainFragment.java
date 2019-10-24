package com.example.moviesearch.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesearch.App;
import com.example.moviesearch.R;
import com.example.moviesearch.adapters.FilmAdapter;
import com.example.moviesearch.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    private Disposable disposable;
    private List<Movie> savedMovies = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity != null) {
            activity.getSupportActionBar().setTitle(R.string.menu_home);
        }

        FilmAdapter.OnMovieClickListener onMovieClickListener =
                (position) -> {
                    FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.llMain, PagerFragment.getInstance(savedMovies, position, App.PARENT_MAIN));
                    fragmentTransaction.commit();
                };

        FilmAdapter adapter = new FilmAdapter(getActivity(), onMovieClickListener);

        getFromDB(adapter);

        RecyclerView recyclerView = view.findViewById(R.id.main_rrView);
        recyclerView.setAdapter(adapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            int fragmentListSize = activity.getSupportFragmentManager().getFragments().size();
            if (fragmentListSize == 1) activity.finish();
        }

        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void getFromDB(FilmAdapter adapter) {
        App.getDb().movieDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(List<Movie> movies) {
                        savedMovies = movies;
                        adapter.setMovies(savedMovies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("myLogs", "onError: " + e.getMessage());

                    }
                });
    }
}
