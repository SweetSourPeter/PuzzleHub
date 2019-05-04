package com.example.peterwang;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class twozerofoureight extends AppCompatActivity {

    public twozerofoureight () {
        twozerofoureight = this;

    }


    public static final String PREFS_NAME = "twozerofoureightFile";

    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twozerofoureight);

        //create a complete pop dialog
        myDialog = new Dialog(this);

        //save Data

        SharedPreferences bestScore = getSharedPreferences(PREFS_NAME, 0);
        best = bestScore.getInt("savedBest", savedBest);

        //best = getBest(savedBest);
        //SharedPreferences.Editor editor = bestScore.edit();
        //editor.putInt("savedBest", best);

        ((TextView) findViewById(R.id.bestScore)).setText(best+"");


        tvScore = (TextView) findViewById(R.id.tcScore);
    }



    //recreate activity
    public void recreateActivity (View view) {
        App.refreshApp(this);
    }

    private void setBest() {
        if(score >= best) {
            SharedPreferences bestScore = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = bestScore.edit();
            editor.putInt("savedBest", score);
            editor.commit();
            ((TextView) findViewById(R.id.tcScore)).setTextColor(0xffED6E15);
        }
    }


    public void ShowPopUp(){
        TextView record;
        TextView txtclose;
        ImageButton popNewGame;
        myDialog.setContentView(R.layout.twozerofoureightpopup);
        record = (TextView) myDialog.findViewById(R.id.popRecord);
        popNewGame =(ImageButton) myDialog.findViewById(R.id.popNewGame);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        System.out.println("1");
        record.setText(best+"");
        System.out.println("1");

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        popNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreateActivity(v);

               // Intent intent = new Intent(twozerofoureight.this, twozerofoureight.class);
               // startActivity(intent);
            }
        });


        myDialog.show();
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
