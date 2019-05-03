package com.example.peterwang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class twozerofoureight extends AppCompatActivity {

    public twozerofoureight () {
        twozerofoureight = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twozerofoureight);
        tvScore = (TextView) findViewById(R.id.tcScore);
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
    }

    public void addScore (int s) {
        score+=s;
        showScore();
    }

    private int score = 0;

    private TextView tvScore;
    private static twozerofoureight twozerofoureight = null;

    public static twozerofoureight getTwozerofoureight() {
        return twozerofoureight;
    }
}
