package com.example.peterwang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gobang extends AppCompatActivity {

    private gobangPanel GobangPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobang);
        GobangPanel = findViewById(R.id.id_gobang);

        Button button = findViewById(R.id.Replay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GobangPanel.restart();
            }
        });

        Button button1 = findViewById(R.id.Undo);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GobangPanel.retract();
            }
        });
    }

}