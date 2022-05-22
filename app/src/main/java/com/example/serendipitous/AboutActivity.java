package com.example.serendipitous;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainActivity.isDarkTheme){
            setTheme(R.style.DarkTheme);
        }
        setContentView(R.layout.activity_about);
    }
}