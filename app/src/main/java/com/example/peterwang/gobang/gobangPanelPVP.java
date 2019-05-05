package com.example.peterwang.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.peterwang.R;

public class gobangPanelPVP extends View implements View.OnTouchListener {


    private Paint paint;
    private int[][] chessArray;
    private boolean isWhite = true;
    private boolean isGameOver = false;


    private Bitmap whiteChess;
    private Bitmap blackChess;
    private Rect rect;
    private float len;
    private int GRID_NUMBER = 13;
    private float preWidth;
    private float offset;
    private GameCallBack callBack;
    private int whiteChessCount, blackChessCount;


    public static final int WHITE_CHESS = 1;
    public static final int BLACK_CHESS = 2;
    public static final int WHITE_WIN = 101;
    public static final int BLACK_WIN = 102;
    public static final int NO_WIN = 103;

    public gobangPanelPVP(Context context) {
        this(context, null);
    }

    public gobangPanelPVP(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public gobangPanelPVP(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        chessArray = new int[GRID_NUMBER][GRID_NUMBER];

        whiteChess = BitmapFactory.decodeResource(context.getResources(), R.drawable.white);
        blackChess = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);

        whiteChessCount = 0;
        blackChessCount = 0;

        rect = new Rect();
        setOnTouchListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int len = width > height ? height : width;

        setMeasuredDimension(len, len);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        len = getWidth() > getHeight() ? getHeight() : getWidth();
        preWidth = len / GRID_NUMBER;

        offset = preWidth / 2;

        for (int i = 0; i < GRID_NUMBER; i++) {
            float start = i * preWidth + offset;
            canvas.drawLine(offset, start, len - offset, start, paint);
            canvas.drawLine(start, offset, start, len - offset, paint);
        }

        for (int i = 0; i < GRID_NUMBER; i++) {
            for (int j = 0; j < GRID_NUMBER; j++) {
                float rectX = offset + i * preWidth;
                float rectY = offset + j * preWidth;

                rect.set((int) (rectX - offset), (int) (rectY - offset),
                        (int) (rectX + offset), (int) (rectY + offset));

                switch (chessArray[i][j]) {
                    case WHITE_CHESS:
                        canvas.drawBitmap(whiteChess, null, rect, paint);
                        break;
                    case BLACK_CHESS:
                        canvas.drawBitmap(blackChess, null, rect, paint);
                        break;
                }
            }
        }
    }


    private void checkGameOver() {
        int chess = isWhite ? BLACK_CHESS : WHITE_CHESS;
        boolean isFull = true;

        for (int i = 0; i < GRID_NUMBER; i++) {
            for (int j = 0; j < GRID_NUMBER; j++) {
                if (chessArray[i][j] != BLACK_CHESS && chessArray[i][j] != WHITE_CHESS) {
                    isFull = false;
                }

                if (chessArray[i][j] == chess) {
                    if (isFiveSame(i, j)) {
                        isGameOver = true;
                        if (callBack != null) {
                            if (chess == WHITE_CHESS) {
                                whiteChessCount++;
                            } else {
                                blackChessCount++;
                            }
                            callBack.GameOver(chess == WHITE_CHESS ? WHITE_WIN : BLACK_WIN);
                        }
                        return;
                    }
                }
            }
        }

        if (isFull) {
            isGameOver = true;
            if (callBack != null) {
                callBack.GameOver(NO_WIN);
            }
        }
    }


    public void resetGame() {
        isGameOver = false;
        for (int i = 0; i < GRID_NUMBER; i++) {
            for (int j = 0; j < GRID_NUMBER; j++) {
                chessArray[i][j] = 0;
            }
        }

        postInvalidate();
    }

    private boolean isFiveSame(int x, int y) {
        if (x + 4 < GRID_NUMBER) {
            if (chessArray[x][y] == chessArray[x + 1][y] && chessArray[x][y] == chessArray[x + 2][y]
                    && chessArray[x][y] == chessArray[x + 3][y] && chessArray[x][y] == chessArray[x + 4][y]) {
                return true;
            }
        }

        if (y + 4 < GRID_NUMBER) {
            if (chessArray[x][y] == chessArray[x][y + 1] && chessArray[x][y] == chessArray[x][y + 2]
                    && chessArray[x][y] == chessArray[x][y + 3] && chessArray[x][y] == chessArray[x][y + 4]) {
                return true;
            }
        }

        if (y + 4 < GRID_NUMBER && x + 4 < GRID_NUMBER) {
            if (chessArray[x][y] == chessArray[x + 1][y + 1] && chessArray[x][y] == chessArray[x + 2][y + 2]
                    && chessArray[x][y] == chessArray[x + 3][y + 3] && chessArray[x][y] == chessArray[x + 4][y + 4]) {
                return true;
            }
        }

        if (y - 4 > 0 && x + 4 < GRID_NUMBER) {
            if (chessArray[x][y] == chessArray[x + 1][y - 1] && chessArray[x][y] == chessArray[x + 2][y - 2]
                    && chessArray[x][y] == chessArray[x + 3][y - 3] && chessArray[x][y] == chessArray[x + 4][y - 4]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isGameOver) {
                    float downX = event.getX();
                    float downY = event.getY();

                    if (downX >= offset / 2 && downX <= len - offset / 2
                            && downY >= offset / 2 && downY <= len - offset / 2) {

                        int x = (int) (downX / preWidth);
                        int y = (int) (downY / preWidth);

                        if (chessArray[x][y] != WHITE_CHESS &&
                                chessArray[x][y] != BLACK_CHESS) {
                            chessArray[x][y] = isWhite ? WHITE_CHESS : BLACK_CHESS;
                            isWhite = !isWhite;

                            postInvalidate();
                            checkGameOver();

                            if (callBack != null) {
                                callBack.ChangeGamer(isWhite);
                            }
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Game Over!",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return false;
    }

    public void setCallBack(GameCallBack callBack) {
        this.callBack = callBack;
    }

    public int getWhiteChessCount() {
        return whiteChessCount;
    }

    public int getBlackChessCount() {
        return blackChessCount;
    }
}
