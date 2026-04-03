package com.koDong.domain.chessPiece;


public class Queen extends Piece {
    public Queen(int x, int y, PieceColor color) {
        super(x, y, PieceType.QUEEN, color);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY) {
        return canMoveStraight(targetX, targetY) ||
                canMoveDiagonal(targetX, targetY);
    }

}
