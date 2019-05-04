package com.example.peterwang;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class twozerofoureightCard extends FrameLayout {
    public twozerofoureightCard(Context context) {
        super(context);

        lable = new TextView(getContext());
        lable.setTextSize(32);
        lable.setBackgroundColor(0x33ffffff);
        lable.setTextColor(0xffFFFFFF);
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

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            lable.setText("");
        } else {
            lable.setText(num + "");
        }

        if (this.num == 0)
            lable.setBackgroundColor(0x33ffffff);
        else if (this.num == 2)
            lable.setBackgroundColor(0xffF0D49F);
        else if(this.num == 4)
            lable.setBackgroundColor(0xffF0B25A);
        else if(this.num == 8)
            lable.setBackgroundColor(0xffF09A52);
        else if (this.num == 16)
            lable.setBackgroundColor(0xffF08631);
        else if (this.num == 32)
            lable.setBackgroundColor(0xffF0C53F);
        else if (this.num == 64)
            lable.setBackgroundColor(0xffF0E53D);
        else if (this.num == 128)
            lable.setBackgroundColor(0xffC0F037);
        else if (this.num == 256)
            lable.setBackgroundColor(0xff63F04F);
        else if (this.num == 512)
            lable.setBackgroundColor(0xff5EF0A0);
        else if (this.num == 1024)
            lable.setBackgroundColor(0xff65B5F0);
        else if (this.num == 2048)
            lable.setBackgroundColor(0xff5B4CF0);
        else if (this.num == 4096)
            lable.setBackgroundColor(0xffBB60F0);
        else if (this.num == 8192)
            lable.setBackgroundColor(0xffF08DB3);
        else if (this.num == 16384)
            lable.setBackgroundColor(0xffF03E82);
        else if (this.num == 32768)
            lable.setBackgroundColor(0xffF00014);
    }


    //check if two cards are equal
    public boolean equals(twozerofoureightCard obj) {
        return getNum() == obj.getNum();
    }

    private TextView lable;
}
