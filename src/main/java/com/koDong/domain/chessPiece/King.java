package com.koDong.domain.chessPiece;


public class King extends Piece {

    public King(int x, int y, PieceColor color) {
        super(x, y, PieceType.KING, color);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY) {
        int absoluteX = Math.abs(targetX - this.getCurrentX());
        int absoluteY = Math.abs(targetY - this.getCurrentY());

        if (absoluteX <= 1 && absoluteY <= 1){
            if (absoluteX == 0 && absoluteY == 0){
                return false;
            }
            return true;
        }
        return false;
    }

}

