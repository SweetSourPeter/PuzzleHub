package com.example.peterwang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button ;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = findViewById(R.id.button_text);
        textView = findViewById(R.id.textView_test);

        testButton.setOnClicklistener(this);
    }


}
