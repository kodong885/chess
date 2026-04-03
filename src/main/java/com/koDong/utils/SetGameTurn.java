package com.koDong.utils;

import com.koDong.domain.GameTurn;

import java.util.Random;

public class SetGameTurn {
    public GameTurn initializeGameTurn() {
        Random r = new Random();
        int tmpN = r.nextInt(10); // 0~9 random number

        if (tmpN % 2 == 0) {
            return GameTurn.WHITE;
        } else {
            return GameTurn.BLACK;
        }
    }

    public GameTurn updateGameTurn(GameTurn currentTurn) {
        if (currentTurn.equals(GameTurn.WHITE)) {
            // 현재 턴이 WHITE이었다면 BLACK으로
            return GameTurn.BLACK;
        } else {
            // 현재 턴이 BLACK이었다면 WHITE로
            return GameTurn.WHITE;
        }
    }


}
