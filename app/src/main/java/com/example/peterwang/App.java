package com.example.peterwang;

import android.support.v7.app.AppCompatActivity;

public class App {
    public static void finshApp(AppCompatActivity appCompatActivity) {
        appCompatActivity.finish();
    }
    public static void refreshApp(AppCompatActivity appCompatActivity) {
        appCompatActivity.recreate();

    }

}
