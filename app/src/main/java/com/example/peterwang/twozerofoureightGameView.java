package com.example.peterwang;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

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
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        addCards(getCardsWidth(), getCardsWidth());

        startGame();

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
                        offsetX = event.getX() - startX;
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

        startGame();

    }

    //add card
    private void addCards(int cardWidth, int cardHeight) {
        twozerofoureightCard c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new twozerofoureightCard(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }

    //game starts
    private void startGame () {

        //clean the game
        for (int i =0; i <4; i++) {
            for(int j = 0; j < 4; j++) {
                cardsMap[i][j].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    //Get Height Function
    private int getCardsWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        //access screen info
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;

        return (cardWidth - 10)/ 4;
    }

    //random num
    private void addRandomNum() {
        emptyPoints.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardsMap[i][j].getNum() <= 0) {
                    emptyPoints.add(new Point(i,j));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }

    private void swipeLeft() {
        
    }
    
    private void swipeRight() {
        
    }
    
    private void swipeUp () {
        
    }
    
    private void swipeDown () {
        
    }

    private twozerofoureightCard[][] cardsMap = new twozerofoureightCard[4][4];
    private List<Point> emptyPoints = new ArrayList<>();
    
}
