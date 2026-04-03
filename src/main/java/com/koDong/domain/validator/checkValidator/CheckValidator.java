package com.koDong.domain.validator.checkValidator;


import com.koDong.domain.GameTurn;
import com.koDong.domain.chessBoard.ChessBoard;
import com.koDong.domain.chessPiece.King;
import com.koDong.domain.chessPiece.Piece;
import com.koDong.domain.chessPiece.PieceColor;
import com.koDong.domain.validator.moveValidator.MoveValidator;

import java.util.ArrayList;
import java.util.List;


public class CheckValidator {

    // 상대가 기물을 place했는데, 그 자리에서 king이 상대 기물의 범위에 있는지 검증
    // 1) 현재 턴 컬러 기물의 모든 피스들을 담고 있는 배열을 만든 뒤,
    // 2) 배열 속 모든 현재 턴 컬러 피스의 공격 범위가 다음 턴 컬러 피스의 king에 도달하는지 체크
    // --> 하나라도 공격 범위에 있으면 return true;
    public boolean isCheck(GameTurn turn, ChessBoard chessBoard) {
        List<Piece> currentTurnPieces = setCurrentTurnPieces(turn, chessBoard);

        King oppositeKing;
        if (turn.equals(GameTurn.WHITE)) {
            // 흰색 피스의 경우
            oppositeKing = chessBoard.getKing(PieceColor.BLACK);
        } else {
            // 검은색 피스의 경우
            oppositeKing = chessBoard.getKing(PieceColor.WHITE);
        }

        int targetX = oppositeKing.getCurrentX();
        int targetY = oppositeKing.getCurrentY();

        MoveValidator moveValidator = new MoveValidator();

        for (Piece piece : currentTurnPieces) {
            // piece들이 king을 공격할 수 있는 범위에 있는지 없는지 판단하는 로직;
            if (piece.canMoveTo(targetX, targetY) && moveValidator.validateMove(chessBoard.getChessBoard(), piece,targetX, targetY)) {
                return true; // 한 피스라도 킹을 공격할 수 있으면 true
            }
        }
        return false;
    }


    // 현재 턴 컬러 기물의 모든 피스들을 담고 있는 배열 만드는 메서드
    private List<Piece> setCurrentTurnPieces(GameTurn turn, ChessBoard chessBoard) {
        List<Piece> currentTurnPieces = new ArrayList<>();

        if (turn.equals(GameTurn.WHITE)) {
            // 흰색 피스의 경우
            Piece piece;
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    piece = chessBoard.findPiece(x, y);
                    if (!(piece == null)) {
                        if (piece.getColor().equals(PieceColor.WHITE)) {
                            currentTurnPieces.add(piece);
                        }
                    }
                }
            }
        } else {
            // 검은색 피스의 경우
            Piece piece;
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    piece = chessBoard.findPiece(x, y);
                    if (!(piece == null)) {
                        if (piece.getColor().equals(PieceColor.BLACK)) {
                            currentTurnPieces.add(piece);
                        }
                    }
                }
            }
        }
        return currentTurnPieces;
    }

}
