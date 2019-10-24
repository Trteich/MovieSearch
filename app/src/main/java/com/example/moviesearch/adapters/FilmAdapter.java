package com.example.moviesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesearch.App;
import com.example.moviesearch.R;
import com.example.moviesearch.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {


    private OnMovieClickListener listener;
    private Context context;
    private List<Movie> movies = new ArrayList<>();


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    public FilmAdapter(Context context, OnMovieClickListener listener) {
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        String imgURL = movie.getPoster();
        Glide.with(context)
                .load(App.ING_URL_PREFIX + imgURL)
                .into(holder.img);

        holder.img.setOnClickListener(v -> listener.onMovieClicked(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.poster);
        }

    }

    public interface OnMovieClickListener{
        void onMovieClicked(int position);
    }

}
