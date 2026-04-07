package com.koDong.domain.chessBoard;

import com.koDong.domain.chessPiece.*;

public class ChessBoard {
    // int[] 형식의 piece 위치들로 chessBoard자원 조회할때, 0인덱스는 y, 1인덱스는 x로 조회해야함.
    private Piece[][] chessBoard = new Piece[8][8];

    public ChessBoard() {
        // Black Pawn
        Piece blackPawn1 = new Pawn(0, 1, PieceColor.BLACK);
        Piece blackPawn2 = new Pawn(1, 1, PieceColor.BLACK);
        Piece blackPawn3 = new Pawn(2, 1, PieceColor.BLACK);
        Piece blackPawn4 = new Pawn(3, 1, PieceColor.BLACK);
        Piece blackPawn5 = new Pawn(4, 1, PieceColor.BLACK);
        Piece blackPawn6 = new Pawn(5, 1, PieceColor.BLACK);
        Piece blackPawn7 = new Pawn(6, 1, PieceColor.BLACK);
        Piece blackPawn8 = new Pawn(7, 1, PieceColor.BLACK);
        initializePiece(blackPawn1, 0, 1);
        initializePiece(blackPawn2, 1, 1);
        initializePiece(blackPawn4, 3, 1);
        initializePiece(blackPawn3, 2, 1);
        initializePiece(blackPawn5, 4, 1);
        initializePiece(blackPawn6, 5, 1);
        initializePiece(blackPawn7, 6, 1);
        initializePiece(blackPawn8, 7, 1);

        // White Pawn
        Piece whitePawn1 = new Pawn(0, 6, PieceColor.WHITE);
        Piece whitePawn2 = new Pawn(1, 6, PieceColor.WHITE);
        Piece whitePawn3 = new Pawn(2, 6, PieceColor.WHITE);
        Piece whitePawn4 = new Pawn(3, 6, PieceColor.WHITE);
        Piece whitePawn5 = new Pawn(4, 6, PieceColor.WHITE);
        Piece whitePawn6 = new Pawn(5, 6, PieceColor.WHITE);
        Piece whitePawn7 = new Pawn(6, 6, PieceColor.WHITE);
        Piece whitePawn8 = new Pawn(7, 6, PieceColor.WHITE);
        initializePiece(whitePawn1, 0, 6);
        initializePiece(whitePawn2, 1, 6);
        initializePiece(whitePawn3, 2, 6);
        initializePiece(whitePawn4, 3, 6);
        initializePiece(whitePawn5, 4, 6);
        initializePiece(whitePawn6, 5, 6);
        initializePiece(whitePawn7, 6, 6);
        initializePiece(whitePawn8, 7, 6);

        // Black Rook
        Piece blackRook1 = new Rook(0, 0, PieceColor.BLACK);
        Piece blackRook2 = new Rook(7, 0, PieceColor.BLACK);
        initializePiece(blackRook1, 0, 0);
        initializePiece(blackRook2, 7, 0);
        // White Rook
        Piece whiteRook1 = new Rook(0, 7, PieceColor.WHITE);
        Piece whiteRook2 = new Rook(7, 7, PieceColor.WHITE);
        initializePiece(whiteRook1, 0, 7);
        initializePiece(whiteRook2, 7, 7);

        // Black Knight
        Piece blackKnight1 = new Knight(1, 0, PieceColor.BLACK);
        Piece blackKnight2 = new Knight(6, 0, PieceColor.BLACK);
        initializePiece(blackKnight1, 1, 0);
        initializePiece(blackKnight2, 6, 0);
        // White Knight
        Piece whiteKnight1 = new Knight(1, 7, PieceColor.WHITE);
        Piece whiteKnight2 = new Knight(6, 7, PieceColor.WHITE);
        initializePiece(whiteKnight1, 1, 7);
        initializePiece(whiteKnight2, 6, 7);

        // Black Bishop
        Piece blackBishop1 = new Bishop(2, 0, PieceColor.BLACK);
        Piece blackBishop2 = new Bishop(5, 0, PieceColor.BLACK);
        initializePiece(blackBishop1, 2, 0);
        initializePiece(blackBishop2, 5, 0);
        // White Bishop
        Piece whiteBishop1 = new Bishop(2, 7, PieceColor.WHITE);
        Piece whiteBishop2 = new Bishop(5, 7, PieceColor.WHITE);
        initializePiece(whiteBishop1, 2, 7);
        initializePiece(whiteBishop2, 5, 7);

        // Black Queen
        Piece blackQueen = new Queen(3, 0, PieceColor.BLACK);
        initializePiece(blackQueen, 3, 0);
        // White Queen
        Piece whiteQueen = new Queen(3, 7, PieceColor.WHITE);
        initializePiece(whiteQueen, 3, 7);

        // Black King
        Piece blackKing = new King(4, 0, PieceColor.BLACK);
        initializePiece(blackKing, 4, 0);
        // White King
        Piece whiteKing = new King(4, 7, PieceColor.WHITE);
        initializePiece(whiteKing, 4, 7);
    }

    /*
   ChessBoard를 초기화 시킬때, 즉 처음 피스를 세팅할 때만 사용해야한다.
     */
    private void initializePiece(Piece selectedPiece, int targetX, int targetY) {
        chessBoard[targetY][targetX] = selectedPiece;
    }

    public Piece[][] getChessBoard() {
        return this.chessBoard;
    }

    // get a piece
    public Piece findPiece(int x, int y) {
        return chessBoard[y][x];
    }

    /**
     * 타켓 위치에 피스를 놓은 후, 해당 피스의 기존 위치에 있는 피스를 삭제함.
     * @param selectedPiece 놓고자하는 피스
     * @param targetX 놓고자하는 x위치
     * @param targetY 놓고자하는 y위치
     */
    public void placePiece(Piece selectedPiece, int targetX, int targetY) {
        // 피스 놓기
        chessBoard[targetY][targetX] = selectedPiece;

        // 기존 피스 위치에 있는 기존 피스 삭제
        int originX = selectedPiece.getCurrentX();
        int originY = selectedPiece.getCurrentY();
        chessBoard[originY][originX] = null;

        // 피스의 x, y필드 업데이트 (위치 업데이트)
        findPiece(targetX, targetY).setX(targetX);
        findPiece(targetX, targetY).setY(targetY);
    }

    public King getKing(PieceColor color) throws NoKingExistException {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = chessBoard[y][x];
                if (!(piece == null)) {
                    if (piece.getColor().equals(color) && piece.getType().equals(PieceType.KING)) {
                        // 파라미터로 받은 피스 컬러의 king인 경우
                        return (King) piece;
                    }
                }

            }
        }
        throw new NoKingExistException("파라미터로 받은 컬러의 킹 피스가 없습니다.");
    }

    /**
     * Checkmate 테스트를 위해서 실제 피스를 움직이는 메서드.
     * 오직 Checkmate관련 로직에서만 사용되어야함.
     * @param selectedPiece 공격을 한 피스
     * @param targetX 공격하고자하는 x위치
     * @param targetY 공격하고자하는 y위치
     * @return 공격당한 피스 반환
     */
    public Piece placePieceForTest(Piece selectedPiece, int targetX, int targetY) {
        Piece attackedPiece = chessBoard[targetY][targetX];
        chessBoard[targetY][targetX] = selectedPiece;
        return attackedPiece;
    }

    /**
     * 테스트를 위해 피스를 옮긴 후(공격), 다시 피스의 위치를 원상복귀하고자 할 때 사용가능.
     * @param selectedPiece 공격을 한 피스
     * @param attackedPiece 공격을 당한 피스
     * @param originX 공격을 한 피스의 공격하기 전 x위치
     * @param originY 공격을 한 피스의 공격하기 전 y위치
     * @param targetX 공격을 당한 피스의 x위치
     * @param targetY 공격을 당한 피스의 y위치
     */
    public void restoreAttack(
            Piece selectedPiece, Piece attackedPiece,
            int originX, int originY,
            int targetX, int targetY
    ) {
        chessBoard[originY][originX] = selectedPiece;
        chessBoard[targetY][targetX] = attackedPiece;
    }

    /**
     * 피스를 놓은 위치를 이용하여 놓인 피스의 전 위치로 되돌리는 메서드
     * 놓은 위치에 아무런 피스가 없어야함(null)
     * @param originX 놓은 피스의 놓이기 전 x위치
     * @param originY 놓은 피스의 놓이기 전 y위치
     * @param targetX 피스를 놓은 x위치
     * @param targetY 피스를 놓은 y위치
     */
    public void restorePlace(
            int originX, int originY,
            int targetX, int targetY
    ) {
        chessBoard[originY][originX] = chessBoard[targetY][targetX];
        chessBoard[targetY][targetX] = null;
    }

}
