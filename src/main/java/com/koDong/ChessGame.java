package com.koDong;

import com.koDong.application.GameService;
import com.koDong.domain.chessBoard.ChessBoard;
import com.koDong.utils.UserInput;

import java.util.Scanner;

public class ChessGame {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserInput userInput = new UserInput(sc);

        ChessBoard chessBoard = new ChessBoard();
        GameService service = new GameService(userInput, chessBoard);

        service.startGame();


    }
}
