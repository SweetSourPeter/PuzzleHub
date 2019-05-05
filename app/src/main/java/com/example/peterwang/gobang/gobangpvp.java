package com.example.peterwang.gobang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peterwang.R;

public class gobangpvp extends AppCompatActivity implements GameCallBack {

    private gobangPanelPVP gobangpanelpvp;
    private TextView whiteWinTv,blackWinTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobangpvp);
        gobangpanelpvp = findViewById(R.id.id_gobangPanelPVP);
        gobangpanelpvp.setCallBack(this);
        whiteWinTv = findViewById(R.id.white_count_tv);
        blackWinTv = findViewById(R.id.black_count_tv);
    }

    @Override
    public void GameOver(int winner) {
        updateWinInfo();
        switch (winner) {
            case gobangPanelPVP.BLACK_WIN:
                showToast("Black wins!");
                break;
            case gobangPanelPVP.NO_WIN:
                showToast("draw");
                break;
            case gobangPanelPVP.WHITE_WIN:
                showToast("White wins!");
                break;
        }
    }


    private void updateWinInfo(){
        whiteWinTv.setText(gobangpanelpvp.getWhiteChessCount()+" ");
        blackWinTv.setText(gobangpanelpvp.getBlackChessCount()+" ");
    }

    @Override
    public void ChangeGamer(boolean isWhite) {

    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restart_game:
                gobangpanelpvp.resetGame();
                break;
        }
    }
}
