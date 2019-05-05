package com.example.peterwang;

import android.graphics.RectF;

public class Paddle {
    private RectF rect;

    // Set the paddle
    private float length;
    private float height;

    private float x;
    private float y;

    private float paddleSpeed;

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    private int paddleMoving = STOPPED;

    public Paddle(int screenX, int screenY){
        length = 130;
        height = 20;

        x = screenX / 2;
        y = screenY - 20;

        rect = new RectF(x, y, x + length, y + height);

        paddleSpeed = 350;
    }

    public RectF getRect(){

        return rect;
    }

    public void setMovementState(int state){

        paddleMoving = state;
    }

    public float OriginPos = x;

    public float getOrigin(){
        return OriginPos;
    }

    public float getX(){
        return x;
    }

    public float getRX(){
        return x+length;
    }

    public void reset(int X){
        x = X/2;
    }

    // This update method will be called from update in BreakoutView
    // It determines if the paddle needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps, float X){
        if(paddleMoving == LEFT){
            x = x - paddleSpeed / fps;
            //
        }

        if(paddleMoving == RIGHT){
            x = x + paddleSpeed / fps;
            //x = x + X/30;
        }

        rect.left = x;
        rect.right = x + length;
    }


}

