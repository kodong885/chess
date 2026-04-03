package com.koDong.domain.validator.moveValidator;

import com.koDong.domain.GameTurn;
import com.koDong.domain.chessBoard.ChessBoard;
import com.koDong.domain.chessPiece.Piece;
import com.koDong.domain.chessPiece.PieceColor;
import com.koDong.domain.chessPiece.PieceType;


public class MoveValidator {

    // 이동하고자하는 위치에는 다른 피스색의 피스만 있어야함.
    // 이동하고자하는 위치로 가는 길에는 피스가 없어야함(나이트 제외).

    /**
     * Piece에 있는 canMoveTo 메서드는 좌표 위치로만 이동할 수 있는지 없는지를 판단했다면,
     * 이 메서드에서는 ChessBoard를 바탕으로 실제로 이동할 수 있는지 없는지를 판단한다.
     * 즉, 이동하고자하는 위치에 피스가 없거나, 같은 컬러 피스가 있는지 검증함
     * @param chessBoard
     * @param selectedPiece 이동하고자하는 피스
     * @param targetX 이동하고자하는 x위치
     * @param targetY 이동하고자하는 y위치
     * @return 이동할 수 있으면 true 반환
     */
    public boolean validateMove(Piece[][] chessBoard, Piece selectedPiece, int targetX, int targetY) {
        MoveValidatorHelper vd = new MoveValidatorHelper();

        if (isPositionOnChessBoard(targetX, targetY)) {
            switch (selectedPiece.getType()) {
                case PieceType.KING, KNIGHT -> {
                    // null인 경우 : 놓을 수 있음.
                    // 다른 컬러 : 놓을 수 있음.
                    // 같은 컬러(X) : 놓을 수 없음.
                    return chessBoard[targetY][targetX] == null || // ArrayIndexOutOfBoundsException
                            !(chessBoard[targetY][targetX].getColor().equals(selectedPiece.getColor()));
                }

                case PieceType.QUEEN -> {
                    return vd.northWestValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.northEastValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.southWestValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.southEastValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.eastValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.westValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.southValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.northValidator(chessBoard, selectedPiece, targetX, targetY);
                }

                case PieceType.ROOK -> {
                    return vd.eastValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.westValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.southValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.northValidator(chessBoard, selectedPiece, targetX, targetY);
                }

                case PieceType.BISHOP -> {
                    return vd.northWestValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.northEastValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.southWestValidator(chessBoard, selectedPiece, targetX, targetY) ||
                            vd.southEastValidator(chessBoard, selectedPiece, targetX, targetY);
                }

                case PieceType.PAWN -> {
                    int x = Math.abs(selectedPiece.getCurrentX() - targetX);

                    // 흰색 피스의 경우
                    if (selectedPiece.getColor().equals(PieceColor.WHITE)) {
                        // 대각선 이동의 경우 (공격)
                        if (x == 1) {
                            return chessBoard[targetY][targetX].getColor().equals(PieceColor.BLACK); // FIXME (여기서 NPE가 발생할때 처리로직을 작성해야한다!)
                        } else {
                            // 직선 이동
                            return chessBoard[targetY][targetX] == null;
                        }
                    } else {
                        // 검은색 피스의 경우
                        // 대각선 이동의 경우 (공격)
                        if (x == 1) {
                            return chessBoard[targetY][targetX].getColor().equals(PieceColor.WHITE); // FIXME (여기서 NPE가 발생할때 처리로직을 작성해야한다!)
                        } else {
                            // 직선 이동
                            return chessBoard[targetY][targetX] == null;
                        }
                    }
                }

                default -> throw new IllegalStateException("올바른 피스타입이 아닙니다. (KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN이 아님)\"");
            }

        } else {
            // targetX 또는 targetY (또는 둘 다)가 0부터 7사이의 값이 아닐때 --> 해당 위치로 이동할 수 없음.
            return false;
        }

    }

    /**
     * 선택된 피스가 이동하는 것이 가능한지 판단함. (막혀있는지 판단함)
     * 1. '좌표가 체스보드 내에 있는지 (0~7인지)'인지,
     * 2. 그 좌표에 아무런 피스가 없거나(null), 다른 컬러여야함
     **/
    public boolean canSelectedPieceMove(ChessBoard chessBoard, Piece selectedPiece, GameTurn turn) {
        PieceType currentPieceType = selectedPiece.getType();

        int currentX = selectedPiece.getCurrentX();
        int currentY = selectedPiece.getCurrentY();
        switch (currentPieceType) {
            case PieceType.KING, PieceType.QUEEN -> {
                return canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY+1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX, currentY+1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY+1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY);
            }

            case PieceType.ROOK -> {
                return canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX, currentY+1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY);
            }

            case PieceType.BISHOP -> {
                return canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY+1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY+1);
            }

            case PieceType.KNIGHT -> {
                return canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-2, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY-2) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY-2) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+2, currentY-1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-2, currentY+1) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY+2) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY+2) ||
                        canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+2, currentY+1);
            }

            case PieceType.PAWN -> {
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    // 선택된 피스의 컬러가 WHITE인 경우
                    return canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX, currentY-1) ||
                            canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY-1) ||
                            canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY-1);
                } else {
                    // 선택된 피스의 컬러가 BLACK인 경우
                    return canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX, currentY+1) ||
                            canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX-1, currentY+1) ||
                            canSelectedPieceMoveHelper(chessBoard, selectedPiece, currentX+1, currentY+1);
                }
            }

            default -> throw new IllegalStateException("도달할 수 없는 구문");
        }

    }

    /**
     * canSelectedPieceMove메서드의 로직을 간단하게 만들기 위한 메서드
     */
    private boolean canSelectedPieceMoveHelper(ChessBoard chessBoard, Piece selectedPiece, int targetX, int targetY) {
        if (isPositionOnChessBoard(targetX, targetY)) {
            if (isNullOrDifferentColor(chessBoard, selectedPiece, targetX, targetY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * canSelectedPieceMove의 1번 조건 판단을 위한 메서드
     * '좌표가 체스보드 내에 있는지 (0~7인지)'를 검사함
     */
    private boolean isPositionOnChessBoard(int targetX, int targetY) {
        return (targetX >= 0 && targetX <= 7) && (targetY >= 0 && targetY <= 7);
    }

    /**
     * canSelectedPieceMove의 2번 조건 판단을 위한 메서드
     * '놓고자하는 좌표에 아무런 피스가 없거나(null), 다른 컬러여야함'을 검사함
     */
    private boolean isNullOrDifferentColor(ChessBoard chessBoard, Piece selectedPiece, int targetX, int targetY) {
        return (chessBoard.findPiece(targetX, targetY) == null) ||
                (chessBoard.findPiece(targetX, targetY).getColor() != selectedPiece.getColor());
    }

}

