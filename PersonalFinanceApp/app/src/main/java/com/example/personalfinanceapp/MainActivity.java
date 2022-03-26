package com.example.personalfinanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav_view;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the welcome fragment
        changeFragment(new FragmentA());

        nav_view = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //when user pressed the bottom navigation bar buttons
        nav_view.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.frag1:
                    Log.e("bottomNavigationView","FRAG1");
                    changeFragment(new FragmentA());
                    break;
                case R.id.frag2:
                    Log.e("bottomNavigationView","FRAG2");
                    changeFragment(new FragmentB());
                    break;
                case R.id.frag3:
                    Log.e("bottomNavigationView","FRAG3");
                    changeFragment(new FragmentC());
                    break;
                case R.id.frag4:
                    Log.e("bottomNavigationView","FRAG4");
                    changeFragment(new FragmentD());
                    break;
                case R.id.frag5:
                    Log.e("bottomNavigationView","FRAG5");
                    changeFragment(new FragmentE());
                    break;
            }
            return true;
        });
    }

    private void changeFragment(Fragment fragment) {
        //call the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        getSupportFragmentManager().beginTransaction()
                //each time replace the fragment
                .replace(R.id.frame_layout, fragment, null)
                .commit();
    }
}