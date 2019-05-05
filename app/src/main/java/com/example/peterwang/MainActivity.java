package com.example.peterwang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.peterwang.gobang.gobang;

public class MainActivity extends AppCompatActivity {
    //time for welcome page



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //four button on main page
        ImageButton button1 = findViewById(R.id. gobang);
        ImageButton button2 = findViewById(R.id. connect);
        ImageButton button3 = findViewById(R.id.twozerofoureight);
        ImageButton button4 = findViewById(R.id. shikaku);

        //four activity switch
            button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, gobang.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent = new Intent(MainActivity.this, connect.class);
                  startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, twozerofoureight.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, shikaku.class);
                startActivity(intent);
            }
        });

    }
}
