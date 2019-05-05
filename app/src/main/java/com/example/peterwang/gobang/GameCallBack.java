package com.example.peterwang.gobang;

public interface GameCallBack {
    void GameOver(int winner);
    void ChangeGamer(boolean isWhite);
}