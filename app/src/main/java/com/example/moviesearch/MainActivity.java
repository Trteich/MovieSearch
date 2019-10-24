package com.example.moviesearch;

import android.os.Bundle;

import com.example.moviesearch.fragments.MainFragment;
import com.example.moviesearch.fragments.SearchPersonFragment;
import com.example.moviesearch.fragments.SearchTitleFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MainFragment mainFragment;
    SearchTitleFragment searchTitleFragment;
    SearchPersonFragment searchPersonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mainFragment = new MainFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.add(R.id.llMain, mainFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setPrimaryNavigationFragment(mainFragment);
            fragmentTransaction.commit();
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                fragmentTransaction.replace(R.id.llMain, new MainFragment());
                break;

            case R.id.nav_search_title:
                searchTitleFragment = new SearchTitleFragment();
                fragmentTransaction.replace(R.id.llMain, searchTitleFragment);
                break;

            case R.id.nav_search_person:
                searchPersonFragment = new SearchPersonFragment();
                fragmentTransaction.replace(R.id.llMain, searchPersonFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
