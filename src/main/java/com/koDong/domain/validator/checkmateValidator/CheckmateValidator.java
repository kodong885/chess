package com.koDong.domain.validator.checkmateValidator;

import com.koDong.domain.GameTurn;
import com.koDong.domain.chessBoard.ChessBoard;
import com.koDong.domain.chessPiece.*;
import com.koDong.domain.validator.checkValidator.CheckValidator;
import com.koDong.domain.validator.moveValidator.MoveValidator;
import com.koDong.domain.validator.moveValidator.MoveValidatorHelper;

import java.util.ArrayList;
import java.util.List;

public class CheckmateValidator {
    /*
    1. 공격하는 상대 기물을 잡는다.
    2. 킹이 (이동하여) 피한다.
    3. 상대가 공격하는 길목을 차단한다.
    --> 이 경우들이 모두 안될 때 --> 체크메이트
     */
    public boolean isCheckmate(GameTurn turn, ChessBoard chessBoard) {
        System.out.println(!canCatchOpponent(turn, chessBoard));
        System.out.println(!canKingAvoidOpponent(turn, chessBoard));
        System.out.println(!canBlockOpponentPath(turn, chessBoard));

        return !canCatchOpponent(turn, chessBoard) &&
                !canKingAvoidOpponent(turn, chessBoard) &&
                !canBlockOpponentPath(turn, chessBoard);
    }

    // 1. 공격하는 상대 피스를 잡아서 체크에서 풀려나는지.
    // --> king을 공격할 수 있는 피스들을 공격할 수 있고, 또 공격했을때 체크에서 풀려나는지
    // (좀 더 구체적으로 for문에서)
    // -1. king을 공격할 수 있는 피스를 공격한다. (실제 위치도 바꿈)
    // -2. 이 경우 체크에서 풀려나는지?
    // --> 풀려나면 return true
    private boolean canCatchOpponent(GameTurn turn, ChessBoard chessBoard) {
        MoveValidator mv = new MoveValidator();
        CheckValidator cv = new CheckValidator();

        List<Piece> piecesCanAttackOpponentKing = setPiecesCanAttackKing(turn, chessBoard);
        List<Piece> opponentSidePieces = setOpponentSidePieces(turn, chessBoard);

        int targetX;
        int targetY;
        int originX; // 공격하는 피스의 x위치
        int originY; // 공격하는 피스의 y위치
        for (Piece pieceCanAttackKing : piecesCanAttackOpponentKing) {
            targetX = pieceCanAttackKing.getCurrentX();
            targetY = pieceCanAttackKing.getCurrentY();
            for (Piece opponentPiece : opponentSidePieces) {
                // 실제로 이동할 수 있는 경우, 옮겨보고(공격) 체크판단
                if (
                        opponentPiece.canMoveTo(targetX, targetY) && mv.validateMove(chessBoard.getChessBoard(), opponentPiece, targetX, targetY)
                ) {
                    originX = opponentPiece.getCurrentX();
                    originY = opponentPiece.getCurrentY();
                    // 피스 공격하기 (check에서 풀려나는지 직접 확인하기 위해)
                    Piece attackedPiece = chessBoard.placePieceForTest(opponentPiece, targetX, targetY);
                    if (!cv.isCheck(turn, chessBoard)) {
                        // 체크에서 풀려난 경우
                        // 다시 피스 제자리로
                        chessBoard.restoreAttack(attackedPiece, opponentPiece, originX, originY, targetX, targetY);
                        return true;
                    }
                    // 다시 피스 제자리로
                    chessBoard.restoreAttack(attackedPiece, opponentPiece, originX, originY, targetX, targetY);
                }
            }
        }
        return false;
    }

    // 2. 킹이 (이동하여) 피한다.
    /*
        킹이 이동하면서 단순히 이동을 할 수도 있고, 공격을 할 수도 있기 때문에,
        이에 따른 다시 복구 로직도 달라야함.
     */
    private boolean canKingAvoidOpponent(GameTurn turn, ChessBoard chessBoard) {
        King king;
        if (turn.equals(GameTurn.WHITE)) {
            // WHITE 턴의 경우
            king = chessBoard.getKing(PieceColor.BLACK);
        } else {
            // BLACK 턴의 경우
            king = chessBoard.getKing(PieceColor.WHITE);
        }

        int originX = king.getCurrentX();
        int originY = king.getCurrentY();

        MoveValidator mv = new MoveValidator();
        return canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX-1, originY-1) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX, originY-1) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX+1, originY-1) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX+1, originY) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX+1, originY+1) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX, originY+1) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX-1, originY+1) ||
                canKingAvoidOpponentHelper(mv, turn, chessBoard, king, originX-1, originY);
    }

    /**
     * 킹이 매개변수로 받은 x, y위치로 이동하였을때 체크에서 벗어나는지 확인하는 메서드
     * canKingAvoidOpponent에서만 사용할 수 있다.
     * @param mv
     * @param turn
     * @param chessBoard
     * @param king
     * @param targetX 킹이 이동하고자하는 x위치
     * @param targetY 킹이 이동하고자하는 y위치
     * @return
     */
    private boolean canKingAvoidOpponentHelper(MoveValidator mv, GameTurn turn, ChessBoard chessBoard, King king, int targetX, int targetY) {
        CheckValidator cv = new CheckValidator();

        if (king.canMoveTo(targetX, targetY) && mv.validateMove(chessBoard.getChessBoard(), king, targetX, targetY)) {
            int originX = king.getCurrentX(); // 놓기 전 king의 x위치
            int originY = king.getCurrentY(); // 놓기 전 king의 y위치
            if (chessBoard.findPiece(targetX, targetY) == null) {
                // 놓고자하는 위치에 아무런 피스가 없는 경우(null)
                chessBoard.placePiece(king, targetX, targetY);
                if (!cv.isCheck(turn, chessBoard)) {
                    chessBoard.restorePlace(originX, originY, targetX, targetY);
                    return true;
                }
                chessBoard.restorePlace(originX, originY, targetX, targetY);
            } else {
                // 놓고자하는 위치에 상대편 컬러의 피스가 있는 경우
                Piece attackedPiece = chessBoard.placePieceForTest(king, targetX, targetY);
                if (!cv.isCheck(turn, chessBoard)) {
                    chessBoard.restoreAttack(king, attackedPiece, originX, originY, targetX, targetY);
                    return true;
                }
                chessBoard.restoreAttack(king, attackedPiece, originX, originY, targetX, targetY);
            }
        }
        return false; // 매개변수로 받은 위치로 이동(또는 공격)해서는 체크 상황에서 벗아나지 못함
    }
    // 3. 상대가 공격하는 길목을 차단한다.
    // king을 공격할 수 있는 피스가 킹을 공격하기 위해서 가야하는 길에
    // king과 같은 컬러의 피스를 놓아서 체크가 풀리는지
    private boolean canBlockOpponentPath(GameTurn turn, ChessBoard chessBoard) {
        List<Piece> piecesCanAttackOpponentKing = setPiecesCanAttackKing(turn, chessBoard); // 킹을 공격할 수 있는 피스들(QUEEN, BISHOP, ROOK만 해당함)
        piecesCanAttackOpponentKing.removeIf((piece -> piece.getType().equals(PieceType.KING)));
        piecesCanAttackOpponentKing.removeIf((piece -> piece.getType().equals(PieceType.KNIGHT)));
        piecesCanAttackOpponentKing.removeIf((piece -> piece.getType().equals(PieceType.PAWN)));
        List<Piece> opponentSidePieces = setOpponentSidePieces(turn, chessBoard); // king쪽 컬러 피스들 (길목 차단용)

        King king;
        if (turn.equals(GameTurn.WHITE)) {
            // WHITE 턴의 경우
            king = chessBoard.getKing(PieceColor.BLACK);
        } else {
            // BLACK 턴의 경우
            king = chessBoard.getKing(PieceColor.WHITE);
        }

        CheckValidator cv = new CheckValidator();
        List<int[]> path; // 킹을 공격할 수 있는 피스의 킹까지의 공격 경로를 요소로 가지고 있는 리스트
        int targetX; // opponentPiece를 놓기 위한 path 요소 하나의 x위치
        int targetY; // opponentPiece를 놓기 위한 path 요소 하나의 y위치
        int originX; // opponentPiece를 놓기 전, 원래 opponentPiece의 x위치
        int originY; // opponentPiece를 놓기 전, 원래 opponentPiece의 y위치
        for (Piece pieceCanAttackKing : piecesCanAttackOpponentKing) {
            path = setPathForAttackKing(chessBoard, pieceCanAttackKing, king);
            for (Piece opponentPiece : opponentSidePieces) {
                for (int i = 0; i < path.size(); i++) {
                    // 킹을 공격할 수 있는 피스 경로에 opponentPiece를 하나하나씩 place해보면서 체크에서 벗어나는지 체크하기
                    targetX = path.get(i)[0];
                    targetY = path.get(i)[1];
                    originX = opponentPiece.getCurrentX();
                    originY = opponentPiece.getCurrentY();
                    chessBoard.placePiece(opponentPiece, targetX, targetY);
                    if (!cv.isCheck(turn, chessBoard)) {
                        chessBoard.restorePlace(originX, originY, targetX, targetY);
                        return true;
                    }
                    chessBoard.restorePlace(originX, originY, targetX, targetY);
                }
            }
        }
        return false;
    }

    /**
     * 킹을 공격할 수 있는 피스의 킹까지의 공격 범위에 해당하는 타일 반환
     * @param piece 킹을 공격할 수 있는 피스 (QUEEN, BISHOP, ROOK만 해당함)
     * @return 킹을 공격할 수 있는 피스의 킹까지의 공격 범위에 해당하는 타일 반환
     */
    private List<int[]> setPathForAttackKing(ChessBoard chessBoard, Piece piece, King king) throws IllegalStateException {
        MoveValidatorHelper vd = new MoveValidatorHelper();

        int targetX = king.getCurrentX();
        int targetY = king.getCurrentY();
        List<int[]> path = new ArrayList<>();
        switch (piece.getType()) {
            case PieceType.QUEEN -> {
                int i = 0;
                int x;
                int y;
                if (vd.eastValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 동쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()+i;
                        y = piece.getCurrentY();
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.westValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 서쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()-i;
                        y = piece.getCurrentY();
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.southValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 남쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX();
                        y = piece.getCurrentY()+i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.northValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 북쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX();
                        y = piece.getCurrentY()-i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.northEastValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 북동쪽 ↗
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()+i;
                        y = piece.getCurrentY()-i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.northWestValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 북서쪽 ↖
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()-i;
                        y = piece.getCurrentY()-i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.southEastValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 남동쪽 ↘
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()+i;
                        y = piece.getCurrentY()+i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.southWestValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 남서쪽 ↙
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()-i;
                        y = piece.getCurrentY()+i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else {
                    throw new IllegalStateException("앞로직에서 킹을 공격할 수 있는 피스 리스트의 요소를 이용하여 킹을 공격할 수있는 경로를 리턴해야하는데, 킹을 공격할 수 있는 경로가 없다는 것은 말이 안된다.");
                }

            }

            case PieceType.BISHOP -> {
                // king을 공격할 수 있는(체크 시킨) 피스가
                // 실제로 킹을 공격할 수 있는 방향의 path만 만들어서 리턴함
                int i = 0;
                int x;
                int y;
                if (vd.northEastValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 북동쪽 ↗
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()+i;
                        y = piece.getCurrentY()-i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.northWestValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 북서쪽 ↖
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()-i;
                        y = piece.getCurrentY()-i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.southEastValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 남동쪽 ↘
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()+i;
                        y = piece.getCurrentY()+i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.southWestValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 남서쪽 ↙
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()-i;
                        y = piece.getCurrentY()+i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else {
                    throw new IllegalStateException("앞로직에서 킹을 공격할 수 있는 피스 리스트의 요소를 이용하여 킹을 공격할 수있는 경로를 리턴해야하는데, 킹을 공격할 수 있는 경로가 없다는 것은 말이 안된다. ");
                }
            }

            case PieceType.ROOK -> {
                int i = 0;
                int x;
                int y;
                if (vd.eastValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 동쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()+i;
                        y = piece.getCurrentY();
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.westValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 서쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX()-i;
                        y = piece.getCurrentY();
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.southValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 남쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX();
                        y = piece.getCurrentY()+i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else if (vd.northValidator(chessBoard.getChessBoard(), piece, targetX, targetY) && piece.canMoveTo(targetX, targetY)) {
                    // 북쪽
                    int[] position = new int[2];
                    while (true) {
                        i++;
                        x = piece.getCurrentX();
                        y = piece.getCurrentY()-i;
                        if ((x == targetX) && (y == targetY)) {
                            return path;
                        }
                        position[0] = x;
                        position[1] = y;
                        path.add(position);
                    }
                } else {
                    throw new IllegalStateException("앞로직에서 킹을 공격할 수 있는 피스 리스트의 요소를 이용하여 킹을 공격할 수있는 경로를 리턴해야하는데, 킹을 공격할 수 있는 경로가 없다는 것은 말이 안된다.");
                }
            }

            default -> throw new IllegalStateException("이 메서드의 매개변수로 받는 piece는 반드시 QUEEN, BISHOP, ROOK이기 때문에 이 구문은 도달할 수 없음.");
        }
    }

    // 게임 turn에 따라서 king을 공격할 수 있는 피스 리스트 반환.
    private List<Piece> setPiecesCanAttackKing(GameTurn turn, ChessBoard chessBoard) {
        List<Piece> currentTurnPieces = setCurrentTurnPieces(turn, chessBoard);

        King oppositeKing;
        if (turn.equals(GameTurn.WHITE)) {
            // 흰색 피스의 경우
            oppositeKing = chessBoard.getKing(PieceColor.BLACK);
        } else {
            // 검은색 피스의 경우
            oppositeKing = chessBoard.getKing(PieceColor.WHITE);
        }

        int targetX = oppositeKing.getCurrentX();
        int targetY = oppositeKing.getCurrentY();

        MoveValidator moveValidator = new MoveValidator();

        List<Piece> piecesCanAttackKing = new ArrayList<>();
        for (Piece piece : currentTurnPieces) {
            // piece들이 king을 공격할 수 있는 범위에 있는지 없는지 판단하는 로직;
            if (piece.canMoveTo(targetX, targetY) && moveValidator.validateMove(chessBoard.getChessBoard(), piece,targetX, targetY)) {
                piecesCanAttackKing.add(piece);
            }
        }

        return piecesCanAttackKing;
    }

    // 현재 턴 컬러의 모든 피스들을 담고 있는 피스 리스트 반환. (setKingAttackablePieces메서드에만 사용가능함)
    private List<Piece> setCurrentTurnPieces(GameTurn turn, ChessBoard chessBoard) {
        List<Piece> currentTurnPieces = new ArrayList<>();
        if (turn == GameTurn.WHITE) {
            // 흰색 피스의 경우
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Piece piece = chessBoard.findPiece(x, y);
                    if (!(piece == null)) {
                        if (piece.getColor() == PieceColor.WHITE) {
                            currentTurnPieces.add(piece);
                        }
                    }
                }
            }

        } else {
            // 검은색 피스의 경우
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Piece piece = chessBoard.findPiece(x, y);
                    if (!(piece == null)) {
                        if (piece.getColor() == PieceColor.BLACK) {
                            currentTurnPieces.add(piece);
                        }
                    }
                }
            }

        }
        return currentTurnPieces;
    }

    /**
     * 체크 상태의 king쪽과 같은 컬러의 모든 피스들의 리스트를 반환한다. 물론 king도 포함되어있다.
     * @param turn
     * @param chessBoard
     * @return 체크 상태의 king쪽과 같은 컬러의 피스들의 리스트를 반환 (좀 더 구체적으로, 현재 turn이 WHITE이면, BLACK쪽 피스들을 반환하고,
     * 현재 turn이 BLACK이면, WHITE쪽 피스들을 반환한다.
     */
    private List<Piece> setOpponentSidePieces(GameTurn turn, ChessBoard chessBoard) {
        // 현재 turn이 WHITE이면, BLACK쪽 피스들을 반환하고,
        // 현재 turn이 BLACK이면, WHITE쪽 피스들을 반환한다.

        List<Piece> opponentSidePieces = new ArrayList<>();
        if (turn.equals(GameTurn.WHITE)) {
            // 흰색 피스의 경우 4
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Piece piece = chessBoard.findPiece(x, y);
                    if (!(piece == null)) {
                        if (piece.getColor().equals(PieceColor.BLACK)) {
                            opponentSidePieces.add(piece);
                        }
                    }

                }
            }
        } else {
            // 검은색 피스의 경우
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Piece piece = chessBoard.findPiece(x, y);
                    if (!(piece == null)) {
                        if (piece.getColor().equals(PieceColor.WHITE)) {
                            opponentSidePieces.add(piece);
                        }
                    }
                }
            }
        }
        return opponentSidePieces;
    }
}
