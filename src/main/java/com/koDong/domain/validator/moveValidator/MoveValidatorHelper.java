package com.koDong.domain.validator.moveValidator;

import com.koDong.domain.chessPiece.Piece;
import com.koDong.domain.chessPiece.PieceColor;

// MoveValidator 내에 있는 메서드들의 코드를 줄이기 위해서 만든 클래스
// 각 방향으로 이동할 수 있는지 판단함
public class MoveValidatorHelper {

    // 북서쪽 - -
    public boolean northWestValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = originX - i;
            y = originY - i;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);
            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }
    }

    // 북동쪽 + -
    public boolean northEastValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = originX + i;
            y = originY - i;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }

    }

    // 남서쪽 - +
    public boolean southWestValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = originX - i;
            y = originY + i;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }

        }
    }

    // 남동쪽 + +
    public boolean southEastValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = originX + i;
            y = originY + i;
            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }

        }
    }

    // 동쪽
    public boolean eastValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = originX + i;
            y = originY;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }

    }

    // 서쪽
    public boolean westValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i ++;
            x = originX - i;
            y = originY;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }

    }

    // 남쪽
    public boolean southValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i ++;
            x = originX;
            y = originY - i;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);
            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }

    }

    // 북쪽
    public boolean northValidator(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();

        int i = 0;
        int x;
        int y;
        while (true) {
            i ++;
            x = originX;
            y = originY - i;

            // 이동하고자하는 위치인 경우 --> 놓고자하는 피스와 이동하고자하는 위치에 있는 피스의 컬러가 달라야함.
            if ((x == targetX) && (y == targetY)) {
                return isTargetPieceDifferent(chessBoard, selectedPiece.getColor(), targetX, targetY);
            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }
    }

    /**
     * 타켓 위치에 있는 피스가 놓고자하는 피스와 다른 컬러인지 검증
     * (해당 위치의 피스가 null이 아니어야하고, 다른 컬러여야함)
     * @return 해당 위치에 다른 피스의 컬러가 있는 경우 true반환
     */
    private boolean isTargetPieceDifferent(Piece[][] chessBoard, PieceColor selectedPieceColor, int targetX, int targetY) {
        try {
            return chessBoard[targetY][targetX].getColor() != selectedPieceColor;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
