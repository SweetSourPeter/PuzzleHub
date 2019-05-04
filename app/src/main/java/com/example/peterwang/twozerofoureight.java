package com.example.peterwang;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class twozerofoureight extends AppCompatActivity {

    public twozerofoureight () {
        twozerofoureight = this;

    }


    public static final String PREFS_NAME = "twozerofoureightFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twozerofoureight);


        SharedPreferences bestScore = getSharedPreferences(PREFS_NAME, 0);
        best = bestScore.getInt("savedBest", savedBest);

        //best = getBest(savedBest);
        //SharedPreferences.Editor editor = bestScore.edit();
        //editor.putInt("savedBest", best);

        ((TextView) findViewById(R.id.bestScore)).setText(best+"");


        tvScore = (TextView) findViewById(R.id.tcScore);
    }

    private void setBest() {
        if(score >= best) {
            SharedPreferences bestScore = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = bestScore.edit();
            editor.putInt("savedBest", score);
            editor.commit();
        }
    }

    private int getBest(int savedBest) {
        if (score >= savedBest)
             return score;
        else
            return savedBest;
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
        setBest();
    }

    private int score = 0;
    private int savedBest = 0;
    private int best = 0;

    private TextView tvScore;
    private static twozerofoureight twozerofoureight = null;

    public static twozerofoureight getTwozerofoureight() {
        return twozerofoureight;
    }
}
