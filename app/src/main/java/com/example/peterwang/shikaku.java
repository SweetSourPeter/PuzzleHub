package com.example.peterwang;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class shikaku extends Activity {

    BreakoutView breakoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakoutView = new BreakoutView(this);
        setContentView(breakoutView);

    }

    class BreakoutView extends SurfaceView implements Runnable {

        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        boolean paused = true;
        Canvas canvas;
        Paint paint;

        long fps;
        private long timeThisFrame;

        //set screen spec
        int screenX;
        int screenY;

        //set objects
        Paddle paddle;
        Ball ball;

        //set bricks
        Brick[] bricks = new Brick[200];
        int numBricks = 0;

        //set stats data
        int score = 0;
        int lives = 3;

        //game view
        public BreakoutView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;
            paddle = new Paddle(screenX, screenY);

            ball = new Ball();
            createBricksAndRestart();

        }

        //restart
        public void createBricksAndRestart() {
            //reset objects
            ball.reset(screenX, screenY);
            paddle.reset(screenX);

            //reset brick
            int brickWidth = screenX / 8;
            int brickHeight = screenY / 10;

            //create bricks
            numBricks = 0;
            for (int column = 0; column < 8; column++) {

                for (int row = 0; row < 3; row++) {
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numBricks++;
                }
            }

            //reset stats
            if (lives == 0) {
                score = 0;
                lives = 3;
            }
        }

        //state detection and time the frame
        @Override
        public void run() {
            while (playing) {
                long startFrameTime = System.currentTimeMillis();
                if (!paused) {
                    update();
                }
                draw();

                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        //basic running part
        public void update() {

            paddle.update(fps, screenX);
            ball.update(fps);

            //when ball hit brick
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                        bricks[i].setInvisible();
                        ball.reverseYVelocity();
                        score = score + 10;

                    }
                }
            }

            //bounce back
            if (RectF.intersects(paddle.getRect(), ball.getRect())) {
                ball.setRandomXVelocity();
                ball.reverseYVelocity();
                ball.clearObstacleY(paddle.getRect().top - 2);

            }
            // Bounce back if hit bottom
            if (ball.getRect().bottom > screenY) {
                ball.reverseYVelocity();
                ball.clearObstacleY(screenY - 2);

                // Lose a life
                lives--;

                //game over
                if (lives == 0) {
                    paused = true;
                }
            }

            // bounce back if hit top
            if (ball.getRect().top < 0)

            {
                ball.reverseYVelocity();
                ball.clearObstacleY(12);

            }

            // If the ball hits left wall bounce
            if (ball.getRect().left < 0)

            {
                ball.reverseXVelocity();
                ball.clearObstacleX(2);

            }

            // If the ball hits right wall bounce
            if (ball.getRect().right > screenX - 10) {

                ball.reverseXVelocity();
                ball.clearObstacleX(screenX - 22);

            }

            // Pause if cleared screen
            if (score == numBricks * 10)

            {
                paused = true;
                createBricksAndRestart();
            }

        }

        // Draw the newly updated scene
        public void draw() {

            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.argb(255, 26, 128, 182));
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawRect(paddle.getRect(), paint);
                canvas.drawRect(ball.getRect(), paint);
                paint.setColor(Color.argb(255, 249, 129, 0));

                for (int i = 0; i < numBricks; i++) {
                    if (bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }

                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(40);
                canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);

                if (score == numBricks * 10) {
                    paint.setTextSize(90);
                    canvas.drawText("YOU HAVE WON!", 10, screenY / 2, paint);
                }

                // Has the player lost?
                if (lives <= 0) {
                    paint.setTextSize(90);
                    canvas.drawText("YOU HAVE LOST!", 10, screenY / 2, paint);
                    canvas.drawText("Touch screen to restart", 20, screenY/2-90, paint);
                }

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }





        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                        case MotionEvent.ACTION_DOWN:
                            paused = false;



                                if ((motionEvent.getX()>screenX/2)){
                                    if(paddle.getRX()<screenX)
                                        paddle.setMovementState(paddle.RIGHT);
                                    else
                                        paddle.setMovementState(paddle.STOPPED);
                                }

                                else if ((motionEvent.getX()<screenX/2)){
                                    if(paddle.getX()>0)
                                        paddle.setMovementState(paddle.LEFT);
                                    else
                                        paddle.setMovementState(paddle.STOPPED);

                                }




                            break;

                        // Player has removed finger from screen
                        case MotionEvent.ACTION_UP:

                            paddle.setMovementState(paddle.STOPPED);
                            break;
                    }

            return true;

        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        breakoutView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        breakoutView.pause();
    }

}
