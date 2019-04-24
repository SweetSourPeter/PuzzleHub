package com.example.peterwang;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class twozerofoureightCard extends FrameLayout {
    private TextView lable;
    public twozerofoureightCard(Context context) {
        super(context);
        lable = new TextView(getContext());
        lable.setTextSize(32);
        lable.setBackgroundColor(0x33ffffff);
        lable.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        addView(lable, lp);
        setNum(0);
    }

    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int number) {
        this.num = number;
        String s = Integer.toString(num);
        lable.setText(s);
    }

    public boolean equals(twozerofoureightCard obj) {
        return getNum() == obj.getNum();
    }

}
