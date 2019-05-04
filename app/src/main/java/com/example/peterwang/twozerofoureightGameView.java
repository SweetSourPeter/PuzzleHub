package com.example.peterwang;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class twozerofoureightGameView extends GridLayout {
// implements View.OnTouchListener

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


    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        addCards(getCardsWidth(), getCardsWidth());

        initGameView();
    }*/




    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        addCards(getCardsWidth(), getCardsWidth());

        startGame();

        //setOnTouchListener(this);


        setOnTouchListener(new View.OnTouchListener() {

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
                                System.out.println("Left");
                                swipeLeft();
                            } else if (offsetX > 5) {
                                System.out.println("Right");
                                swipeRight();
                            }

                        } else {
                            if (offsetY < -5) {
                                System.out.println("UP");
                                swipeUp();
                            } else if (offsetY > 5) {
                                System.out.println("Down");
                                swipeDown();
                            }
                        }
                        v.performClick();
                        break;
                }

                return false;
            }
        });
    }
/*
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10) / 4;

        addCards(cardWidth, cardWidth);

        startGame();
    }*/

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
        //twozerofoureight.getTwozerofoureight().clearScore();
        System.out.println("game Starts");
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

        //test
        //cardsMap[1][1].setNum(20);
        //cardsMap[2][1].setNum(20);
        //cardsMap[3][1].setNum(20);

        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }

    private void swipeLeft() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            //System.out.println("Row" + y);

            for (int x = 0; x < 4; x++) {
                //System.out.println("Column" + x);

                for (int x1 = x + 1; x1 < 4; x1++) {
                        if (cardsMap[x1][y].getNum()>0) {

                            //if empty
                            if (cardsMap[x][y].getNum() <= 0) {
                                cardsMap[x][y].setNum((cardsMap[x1][y].getNum()));
                                cardsMap[x1][y].setNum(0);
                               // System.out.println(  x1 + "" + y + "" +cardsMap[x1][y].getNum());
                                //System.out.println( x + "" + y + "" +cardsMap[x][y].getNum());

                                x--;//make it run again
                                merge = true;

                            } else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                                cardsMap[x][y].setNum((cardsMap[x][y].getNum())*2);
                                cardsMap[x1][y].setNum(0);
                                //System.out.println( "ss");
                                twozerofoureight.getTwozerofoureight().addScore(cardsMap[x][y].getNum());
                                merge = true;

                            }
                            break;
                        }
                }
            }
        }
        if (merge) {
            System.out.println("left merge called");
            addRandomNum();
            //checkComplete();
        }
        checkComplete();
    }
    
    private void swipeRight() {

        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            //System.out.println("Row" + y);

            for (int x = 3; x >=0; x--) {
                //System.out.println("Column" + x);

                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum()>0) {

                        //if empty
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum((cardsMap[x1][y].getNum()));
                            cardsMap[x1][y].setNum(0);
                            // System.out.println(  x1 + "" + y + "" +cardsMap[x1][y].getNum());
                            //System.out.println( x + "" + y + "" +cardsMap[x][y].getNum());

                            x++;//make it run again
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum((cardsMap[x][y].getNum())*2);
                            cardsMap[x1][y].setNum(0);
                            //System.out.println( "ss");
                            twozerofoureight.getTwozerofoureight().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            System.out.println("right merge called");
            addRandomNum();
            //checkComplete();
        }
        checkComplete();
    }
    
    private void swipeUp () {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            //System.out.println("Row" + y);

            for (int y = 0; y < 4; y++) {
                //System.out.println("Column" + x);

                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum()>0) {

                        //if empty
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum((cardsMap[x][y1].getNum()));
                            cardsMap[x][y1].setNum(0);
                            // System.out.println(  x1 + "" + y + "" +cardsMap[x1][y].getNum());
                            //System.out.println( x + "" + y + "" +cardsMap[x][y].getNum());
                            y--;//make it run again
                            merge = true;

                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum((cardsMap[x][y].getNum())*2);
                            cardsMap[x][y1].setNum(0);
                            //System.out.println( "ss");
                            twozerofoureight.getTwozerofoureight().addScore(cardsMap[x][y].getNum());
                            merge = true;

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            //checkComplete();
        }
        checkComplete();
    }

    private void swipeDown () {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            //System.out.println("Row" + y);

            for (int y = 3; y >=0 ; y--) {
                //System.out.println("Column" + x);

                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum()>0) {

                        //if empty
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum((cardsMap[x][y1].getNum()));
                            cardsMap[x][y1].setNum(0);
                            // System.out.println(  x1 + "" + y + "" +cardsMap[x1][y].getNum());
                            //System.out.println( x + "" + y + "" +cardsMap[x][y].getNum());
                            y++;//make it run again
                            merge = true;

                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum((cardsMap[x][y].getNum())*2);
                            cardsMap[x][y1].setNum(0);
                            //System.out.println( "ss");
                            twozerofoureight.getTwozerofoureight().addScore(cardsMap[x][y].getNum());
                            merge = true;

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            //checkComplete();
        }
        checkComplete();
    }

    private void checkComplete() {
        System.out.println("check complete invoked");
        boolean complete = true;
        All:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y])) ||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y])) ||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y+1]))  ){
                    System.out.println("complete invoked");
                    complete = false;
                    break All;
                }

            }
        }

        if(complete) {

            new AlertDialog.Builder(getContext()).setTitle("You").setMessage(" loser");
        }
    }

    private twozerofoureightCard[][] cardsMap = new twozerofoureightCard[4][4];
    private List<Point> emptyPoints = new ArrayList<>();



/*
    //private float startX, startY, offsetX, offsetY;
    private float startX, startY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float offsetX, offsetY;

        System.out.println("testing");
        cardsMap[0][1].setNum(100);
        swipeLeft();

        switch (action) {
            //record the start position
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                //cardsMap[3][0].setNum(90);


                break;

            //the end position and calculate the offset
            case MotionEvent.ACTION_UP:
                offsetX = event.getX() - startX;
                offsetY = event.getY() - startY;

                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (offsetX < -5) {
                        System.out.println("Left");
                        swipeLeft();
                    } else if (offsetX > 5) {
                        System.out.println("Right");
                        swipeRight();
                    }

                } else {
                    if (offsetY < -5) {
                        System.out.println("UP");
                        swipeUp();
                    } else if (offsetY > 5) {
                        System.out.println("Down");
                        swipeDown();
                    }
                }
                //v.performClick();
                return  true;

            default:
                return super.onTouchEvent(event);
        }
        return false;
    }*/

}
