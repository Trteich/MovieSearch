package com.example.moviesearch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviesearch.R;
import com.example.moviesearch.adapters.MyPagerAdapter;
import com.example.moviesearch.model.Movie;

import java.io.Serializable;
import java.util.List;

public class PagerFragment extends Fragment {
    private static final String ARG_MOVIES = "ARG_MOVIES";
    private static final String ARG_POSITION = "ARG_POSITION";
    private static final String ARG_PARENT = "ARG_PARENT";

    static PagerFragment getInstance(List<Movie> movies, int position, int parent) {
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIES, (Serializable) movies);
        bundle.putInt(ARG_POSITION, position);
        bundle.putInt(ARG_PARENT, parent);

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
        return inflater.inflate(R.layout.pager_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) return;


        List<Movie> movies = (List<Movie>) getArguments().getSerializable(ARG_MOVIES);
        int parent = getArguments().getInt(ARG_PARENT);
        int position = getArguments().getInt(ARG_POSITION);

        ViewPager pager = view.findViewById(R.id.view_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager()
                , movies, parent);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);

        super.onViewCreated(view, savedInstanceState);
    }
}
