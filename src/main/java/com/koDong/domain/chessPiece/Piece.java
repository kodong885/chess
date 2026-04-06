package com.koDong.domain.chessPiece;


public abstract class Piece {
    protected int x;
    protected int y;
    protected PieceType type;
    protected PieceColor color;

    protected Piece(int x, int y, PieceType type, PieceColor color) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
    }

    public int getCurrentX() {
        return this.x;
    }

    public int getCurrentY() {
        return this.y;
    }

    public PieceType getType() {
        return this.type;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // FIXME(오버라이딩시, 맨 첫줄에 targetX, targetY가 outOfBoard인지 검사하는 로직작성 필요)
    public abstract boolean canMoveTo(int targetX, int targetY);

    // 오버라이딩한 canMoveTo(int targetX, int targetY);에서만 사용해야함.
    // FIXME (예외 throws 처리 로직 다시 확인 필요)
    protected boolean canMoveStraight (int targetX, int targetY) throws NotQueenOrRookException {
        return canMoveToEastStraight(targetX, targetY) ||
                canMoveToWestStraight(targetX, targetY) ||
                canMoveToSouthStraight(targetX, targetY) ||
                canMoveToNorthStraight(targetX, targetY);
    }

    // 아래 4개의 private 메서드는 위의 canMoveStraight메서드에서만 사용할 수 있음.
    // 동쪽으로 이동할 수 있는지
    private boolean canMoveToEastStraight(int targetX, int targetY) {
        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = this.x+i;
            y = this.y;
            if (x <= 7) {
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    // 서쪽으로 이동할 수 있는지
    private boolean canMoveToWestStraight(int targetX, int targetY) {
        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = this.x-i;
            y = this.y;
            if (x >= 0) {
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    // 북쪽으로 이동할 수 있는지
    private boolean canMoveToNorthStraight(int targetX, int targetY) {
        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = this.x;
            y = this.y-i;
            if (y <= 7) {
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    // 남쪽으로 갈 수 있는지
    private boolean canMoveToSouthStraight(int targetX, int targetY) {
        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            x = this.x;
            y = this.y+i;
            if (y <= 7) {
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    // canMoveTo(int targetX, int targetY);를 오버라이딩한 메서드에서만 사용해야함.
    // ex. 비숍에서 재정의하는 canMoveTo(int targetX, int targetY);에서 사용.
    protected boolean canMoveDiagonal(int targetX, int targetY) {

        int i = 0;
        int x;
        int y;
        while (true) {
            i++;
            // 북서쪽 ↖
            // Queen이 갈 수 있는 범위를 벗어난 건지 아닌지 체크
            x = this.x-i;
            y = this.y-i;
            if ((x >= 0) && (y >= 0) ) {
                // piece를 놓을 위치가 Queen이 갈 수 있는 범위에 있는지
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                break;
            }

            // 북동쪽 ↗
            // Queen이 갈 수 있는 범위를 벗어난 건지 아닌지 체크
            x = this.x+i;
            y = this.y-i;
            if ((x <= 7) && (y >= 0)) {
                // piece를 놓을 위치가 Queen이 갈 수 있는 범위에 있는지
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                break;
            }

            // 남서쪽 ↙
            // Queen이 갈 수 있는 범위를 벗어난 건지 아닌지
            x = this.x-i;
            y = this.y+i;
            if ((x >= 0) && (y <= 7)) {
                // piece를 놓을 위치가 Queen이 갈 수 있는 범위에 있는지
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                break;
            }

            // 남동쪽 ↘
            // Queen이 갈 수 있는 범위를 벗어난 건지 아닌지 체크
            x = this.x+i;
            y = this.y+i;
            if ((x <= 7) && (y <= 7)) {
                // piece를 놓을 위치가 Queen이 갈 수 있는 범위에 있는지
                if ((x == targetX) && (y == targetY)) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }
}

// →←↑↓