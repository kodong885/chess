package com.koDong.domain.validator.moveValidator;

import com.koDong.domain.chessPiece.Piece;

// MoveValidator 내에 있는 메서드들의 코드를 줄이기 위해서 만든 클래스.
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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
            }

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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
            }

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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
            }

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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
            }

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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) { // ArrayIndexOutOfBoundsException..? (움직일 수 없는 피스는 아예 놓는 로직으로 못가도록 서비스에서 막아야할까..?)
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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
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
                return !(chessBoard[y][x].getColor().equals(selectedPiece.getColor()));
            }

            // 이동하고자하는 위치로 가는 길에 피스가 없어야함.
            if (!(chessBoard[y][x] == null)) {
                return false;
            }
        }
    }

}
