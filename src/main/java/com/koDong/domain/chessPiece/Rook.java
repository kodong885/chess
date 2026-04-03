package com.koDong.domain.chessPiece;


public class Rook extends Piece {

    public Rook(int x, int y, PieceColor color) {
        super(x, y, PieceType.ROOK, color);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY) {
        return canMoveStraight(targetX, targetY);
    }

}
