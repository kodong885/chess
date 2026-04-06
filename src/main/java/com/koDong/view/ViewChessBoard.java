package com.koDong.view;

import com.koDong.domain.User.User;
import com.koDong.domain.chessPiece.Piece;
import com.koDong.domain.chessPiece.PieceColor;
import com.koDong.domain.chessPiece.PieceType;

public class ViewChessBoard {
    User whiteUser;
    User blackUser;

    public ViewChessBoard(User whiteUser, User blackUser) {
        this.whiteUser = whiteUser;
        this.blackUser = blackUser;
    }

    public void viewChessBoard(Piece[][] chessBoard) {
        String chessBoardForView = String.format("""
                <----- %s (BLACK) ----->
                   1  2  3  4  5  6  7  8
                1 [%s][%s][%s][%s][%s][%s][%s][%s]
                2 [%s][%s][%s][%s][%s][%s][%s][%s]
                3 [%s][%s][%s][%s][%s][%s][%s][%s]
                4 [%s][%s][%s][%s][%s][%s][%s][%s]
                5 [%s][%s][%s][%s][%s][%s][%s][%s]
                6 [%s][%s][%s][%s][%s][%s][%s][%s]
                7 [%s][%s][%s][%s][%s][%s][%s][%s]
                8 [%s][%s][%s][%s][%s][%s][%s][%s]
                <----- %s (WHITE) ----->
                """,
                blackUser.getName(),

                decidePiece(chessBoard, 0, 0),
                decidePiece(chessBoard, 1, 0),
                decidePiece(chessBoard, 2, 0),
                decidePiece(chessBoard, 3, 0),
                decidePiece(chessBoard, 4, 0),
                decidePiece(chessBoard, 5, 0),
                decidePiece(chessBoard, 6, 0),
                decidePiece(chessBoard, 7, 0),

                decidePiece(chessBoard, 0, 1),
                decidePiece(chessBoard, 1, 1),
                decidePiece(chessBoard, 2, 1),
                decidePiece(chessBoard, 3, 1),
                decidePiece(chessBoard, 4, 1),
                decidePiece(chessBoard, 5, 1),
                decidePiece(chessBoard, 6, 1),
                decidePiece(chessBoard, 7, 1),

                decidePiece(chessBoard, 0, 2),
                decidePiece(chessBoard, 1, 2),
                decidePiece(chessBoard, 2, 2),
                decidePiece(chessBoard, 3, 2),
                decidePiece(chessBoard, 4, 2),
                decidePiece(chessBoard, 5, 2),
                decidePiece(chessBoard, 6, 2),
                decidePiece(chessBoard, 7, 2),

                decidePiece(chessBoard, 0, 3),
                decidePiece(chessBoard, 1, 3),
                decidePiece(chessBoard, 2, 3),
                decidePiece(chessBoard, 3, 3),
                decidePiece(chessBoard, 4, 3),
                decidePiece(chessBoard, 5, 3),
                decidePiece(chessBoard, 6, 3),
                decidePiece(chessBoard, 7, 3),

                decidePiece(chessBoard, 0, 4),
                decidePiece(chessBoard, 1, 4),
                decidePiece(chessBoard, 2, 4),
                decidePiece(chessBoard, 3, 4),
                decidePiece(chessBoard, 4, 4),
                decidePiece(chessBoard, 5, 4),
                decidePiece(chessBoard, 6, 4),
                decidePiece(chessBoard, 7, 4),

                decidePiece(chessBoard, 0, 5),
                decidePiece(chessBoard, 1, 5),
                decidePiece(chessBoard, 2, 5),
                decidePiece(chessBoard, 3, 5),
                decidePiece(chessBoard, 4, 5),
                decidePiece(chessBoard, 5, 5),
                decidePiece(chessBoard, 6, 5),
                decidePiece(chessBoard, 7, 5),

                decidePiece(chessBoard, 0, 6),
                decidePiece(chessBoard, 1, 6),
                decidePiece(chessBoard, 2, 6),
                decidePiece(chessBoard, 3, 6),
                decidePiece(chessBoard, 4, 6),
                decidePiece(chessBoard, 5, 6),
                decidePiece(chessBoard, 6, 6),
                decidePiece(chessBoard, 7, 6),

                decidePiece(chessBoard, 0, 7),
                decidePiece(chessBoard, 1, 7),
                decidePiece(chessBoard, 2, 7),
                decidePiece(chessBoard, 3, 7),
                decidePiece(chessBoard, 4, 7),
                decidePiece(chessBoard, 5, 7),
                decidePiece(chessBoard, 6, 7),
                decidePiece(chessBoard, 7, 7),

                whiteUser.getName()
        );

        System.out.println(chessBoardForView);
    }

    private String decidePiece(Piece[][] chessBoard, int x, int y) {
        PieceType type;
        PieceColor color;
        try {
            type = chessBoard[y][x].getType();
            color = chessBoard[y][x].getColor();
        } catch (NullPointerException e) {
            return " "; // 빈칸 (피스 없음)
        }

        if (color == PieceColor.WHITE) {
            // 컬러가 WHITE일 경우
            switch (type) {
                case PieceType.KING -> {
                    return "♚";
                }
                case PieceType.QUEEN -> {
                    return "♛";
                }
                case PieceType.ROOK -> {
                    return "♜";
                }
                case PieceType.BISHOP -> {
                    return "♝";
                }
                case PieceType.KNIGHT -> {
                    return "♞";
                }
                case PieceType.PAWN -> {
                    return "♟";
                }
                default -> throw new IllegalStateException("도달할 수 없는 구문");
            }
        } else {
            // 컬러가 BLACK일 경우
            switch (type) {
                case PieceType.KING -> {
                    return "♔";
                }
                case PieceType.QUEEN -> {
                    return "♕";
                }
                case PieceType.ROOK -> {
                    return "♖";
                }
                case PieceType.BISHOP -> {
                    return "♗";
                }
                case PieceType.KNIGHT -> {
                    return "♘";
                }
                case PieceType.PAWN -> {
                    return "♙";
                }
                default -> throw new IllegalStateException("도달할 수 없는 구문");
            }
        }

    }

}
