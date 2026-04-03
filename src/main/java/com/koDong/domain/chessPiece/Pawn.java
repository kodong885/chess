package com.koDong.domain.chessPiece;


public class Pawn extends Piece {

    public Pawn(int x, int y, PieceColor color) {
        super(x, y, PieceType.PAWN, color);
    }

    @Override
    // 대각선(공격) 및 직진 이동가능한지. (자표만 따짐, 갈 수 있는지 없는지는 Validator에서 따지자.)
    public boolean canMoveTo(int targetX, int targetY) {
        int x = Math.abs(this.x - targetX);
        int y = Math.abs(this.y - targetY);

        // 직선 이동
        if (x == 0) {
            // 첫 위치
            if (
                    ((this.color.equals(PieceColor.WHITE)) && (this.y == 6)) ||
                            ((this.color.equals(PieceColor.BLACK) && (this.y == 1)))
            ) {
                return y == 1 || y == 2;
            } else {
                // 첫 위치를 제외한 나머지 위치
                return y == 1;
            }

        } else if (x == 1) {
            // 대각선 이동 (공격)
            return y == 1; // return (x == 1) && (y == 1)
        } else {
            return false;
        }

    }


}
