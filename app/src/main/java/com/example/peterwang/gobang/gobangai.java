package com.example.peterwang.gobang;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peterwang.R;


public class gobangai extends AppCompatActivity implements GameCallBack, AICallBack, View.OnClickListener {

    private gobangPanelAI gobangpanelai;
    private TextView userScoreTv, aiScoreTv;
    private ImageView userChessIv, aiChessIv;
    private ImageView userTimeIv, aiTimeIv;
    private AI ai;
    private PopupWindow chooseChess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobangai);
        initViews();

        ai = new AI(gobangpanelai.getChessArray(), this);
        gobangpanelai.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                initPop(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight());
            }
        });
    }

    private void initViews() {
        gobangpanelai = findViewById(R.id.id_gobangPanelAI);
        gobangpanelai.setCallBack(this);

        userScoreTv = findViewById(R.id.user_score_tv);
        aiScoreTv = findViewById(R.id.ai_score_tv);

        userChessIv = findViewById(R.id.user_chess_iv);
        aiChessIv = findViewById(R.id.ai_chess_iv);

        userTimeIv = findViewById(R.id.user_think_iv);
        aiTimeIv = findViewById(R.id.ai_think_iv);

        findViewById(R.id.restart_game).setOnClickListener(this);
    }


    private void initPop(int width, int height) {
        if (chooseChess == null) {
            View view = View.inflate(this, R.layout.pop_choose_chess, null);
            ImageButton white = view.findViewById(R.id.choose_white);
            ImageButton black = view.findViewById(R.id.choose_black);
            white.setOnClickListener(this);
            black.setOnClickListener(this);
            chooseChess = new PopupWindow(view, width, height);
            chooseChess.setOutsideTouchable(false);
            chooseChess.showAtLocation(gobangpanelai, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void GameOver(int winner) {
        updateWinInfo();
        switch (winner) {
            case gobangPanelAI.BLACK_WIN:
                showToast("Black wins!");
                break;
            case gobangPanelAI.NO_WIN:
                showToast("Draw");
                break;
            case gobangPanelAI.WHITE_WIN:
                showToast("White wins!");
                break;
        }
    }


    private void updateWinInfo() {
        userScoreTv.setText(gobangpanelai.getUserScore() + " ");
        aiScoreTv.setText(gobangpanelai.getAiScore() + " ");
    }

    @Override
    public void ChangeGamer(boolean isWhite) {
        ai.aiBout();
        aiTimeIv.setVisibility(View.VISIBLE);
        userTimeIv.setVisibility(View.GONE);
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void aiAtTheBell() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gobangpanelai.postInvalidate();
                gobangpanelai.checkAiGameOver();
                gobangpanelai.setUserBout(true);
                aiTimeIv.setVisibility(View.GONE);
                userTimeIv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restart_game:
                chooseChess.showAtLocation(gobangpanelai, Gravity.CENTER, 0, 0);

                gobangpanelai.resetGame();
                break;
            case R.id.choose_black:
                changeUI(false);
                chooseChess.dismiss();
                break;
            case R.id.choose_white:
                changeUI(true);
                chooseChess.dismiss();
                break;
        }
    }


    private void changeUI(boolean isUserWhite) {
        if (isUserWhite) {
            gobangpanelai.setUserChess(gobangPanelAI.WHITE_CHESS);
            ai.setAiChess(gobangPanelAI.BLACK_CHESS);
            gobangpanelai.setUserBout(true);

            userChessIv.setBackgroundResource(R.drawable.white);
            aiChessIv.setBackgroundResource(R.drawable.black);
            aiTimeIv.setVisibility(View.GONE);
            userTimeIv.setVisibility(View.VISIBLE);
        } else {
            gobangpanelai.setUserChess(gobangPanelAI.BLACK_CHESS);
            gobangpanelai.setUserBout(false);
            ai.setAiChess(gobangPanelAI.WHITE_CHESS);
            ai.aiBout();

            userChessIv.setBackgroundResource(R.drawable.black);
            aiChessIv.setBackgroundResource(R.drawable.white);
            aiTimeIv.setVisibility(View.VISIBLE);
            userTimeIv.setVisibility(View.GONE);
        }
    }
}
