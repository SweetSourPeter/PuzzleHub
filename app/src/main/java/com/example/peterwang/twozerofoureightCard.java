package com.example.peterwang;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

public class twozerofoureightCard extends FrameLayout {
    public twozerofoureightCard(Context context) {
        super(context);
        lable = new TextView(getContext());
        lable.setTextSize(32);

        LayoutParams lp = new LayoutParams(-1, -1);
        addView(lable, lp);
        setNum(0);
    }

    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        lable.setText("" + num);
    }

    public boolean equals(twozerofoureightCard obj) {
        return getNum() == obj.getNum();
    }

    private TextView lable;

}
