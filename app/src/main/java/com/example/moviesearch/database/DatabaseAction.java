package com.example.moviesearch.database;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.moviesearch.App;
import com.example.moviesearch.model.Movie;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;


public class DatabaseAction {

    public static Disposable saveMovie(Context context, Movie movie) {
        return Completable.fromAction(() -> App.getDb().movieDao().insert(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Сохранено", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public static Disposable deleteMovie(Context context, Movie movie, FragmentManager fragmentManager) {
        return Completable.fromAction(() -> App.getDb().movieDao().delete(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Удалено", Toast.LENGTH_LONG).show();
                        fragmentManager.popBackStack();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
