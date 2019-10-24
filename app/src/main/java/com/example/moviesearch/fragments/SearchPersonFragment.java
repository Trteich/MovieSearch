package com.example.moviesearch.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesearch.App;
import com.example.moviesearch.R;
import com.example.moviesearch.adapters.FilmAdapter;
import com.example.moviesearch.model.ModelPerson;
import com.example.moviesearch.model.Movie;
import com.example.moviesearch.model.Person;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class SearchPersonFragment extends Fragment {
    private Disposable disposable;
    private FilmAdapter adapter;
    private List<Movie> movies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if(activity != null) {
            activity.getSupportActionBar().setTitle(R.string.action_bar_search);
        }

        FilmAdapter.OnMovieClickListener onMovieClickListener =
                (position) -> {
                    FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.llMain, PagerFragment.getInstance(movies, position, App.PARENT_SEARCH));
                    fragmentTransaction.commit();
                };

        adapter = new FilmAdapter(this.getContext(), onMovieClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.movie_recycler_view);

        recyclerView.setAdapter(adapter);
        if (movies != null) {
            adapter.setMovies(movies);
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_fragment, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        initSearch(searchView);

        super.onCreateOptionsMenu(menu, inflater);
    }


    private void initSearch(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {

                    downloadQuery(query);

                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void downloadQuery(String query){
        Single<Response<ModelPerson>> observable =
                App.getThemovieDBapi().getPersonMovies(App.API_KEY, App.LANG, query);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ModelPerson>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(Response<ModelPerson> response) {
                        ModelPerson modelPerson = response.body();

                        if (modelPerson != null) {
                            List<Person> persons = modelPerson.getPersons();
                            movies = new ArrayList<>();
                            for (Person p : persons){
                                movies.addAll(p.getMovies());
                            }

                            deleteEmpty(movies);
                            adapter.setMovies(movies);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(),
                                "Ошибка загрузки", Toast.LENGTH_LONG).show();
                        Log.d("myLogs", "onError: " + e.getMessage());
                    }
                });
    }


    private void deleteEmpty(List<Movie> list){
        List<Movie> recycle = new ArrayList<>();
        for (Movie m : list){
            if(m.getPoster() == null) recycle.add(m);
        }

        list.removeAll(recycle);
    }

}
