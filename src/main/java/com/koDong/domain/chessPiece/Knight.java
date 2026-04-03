package com.koDong.domain.chessPiece;


public class Knight extends Piece {

    public Knight(int x, int y, PieceColor color) {
        super(x, y, PieceType.KNIGHT, color);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY) {
        if ((Math.abs(this.getCurrentX() - targetX) == 2) && (Math.abs(this.getCurrentY() - targetY) == 1))
            return true;
        if ((Math.abs(this.getCurrentX() - targetX) == 1) && (Math.abs(this.getCurrentY() - targetY) == 2))
            return true;
        return false;
    }

}
