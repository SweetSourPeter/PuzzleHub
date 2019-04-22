package com.example.peterwang;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class twozerofoureightGameView extends GridLayout {
    public twozerofoureightGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initGameView();
    }

    public twozerofoureightGameView(Context context) {
        super(context);

        initGameView();
    }


    public twozerofoureightGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    private void initGameView() {
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    //record the start position
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                        
                    //the end position and calculate the offset    
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startY;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }

                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10) / 4;

        addCards(cardWidth, cardWidth);
    }

    //add card
    private void addCards(int cardWidth, int cardHeight) {

        twozerofoureightCard c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new twozerofoureightCard(getContext());
                c.setNum(2);
                addView(c, cardWidth, cardHeight);
            }
        }
    }

    //4 moving methods
    private void swipeLeft() {
        
    }
    
    private void swipeRight() {
        
    }
    
    private void swipeUp () {
        
    }
    
    private void swipeDown () {
        
    }
    
}
