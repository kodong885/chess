package com.koDong.domain.chessPiece;


public class Bishop extends Piece {
    public Bishop(int x, int y, PieceColor color) {
        super(x, y, PieceType.BISHOP, color);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY) {
        return canMoveDiagonal(targetX, targetY);
    }

}
