package com.example.personalfinanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
BottomNavigationView nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nav_view = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        nav_view.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.frag1:
                    Log.e("bottomNavigationView","FRAG1");
                    break;
                case R.id.frag2:
                    Log.e("bottomNavigationView","FRAG2");
                    break;
                case R.id.frag3:
                    Log.e("bottomNavigationView","FRAG3");
                    break;
                case R.id.frag4:
                    Log.e("bottomNavigationView","FRAG4");
                    break;
                case R.id.frag5:
                    Log.e("bottomNavigationView","FRAG5");
                    break;

            }
            return true;
        });
    }
}