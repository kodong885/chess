package com.koDong.domain.chessBoard;

public class NoKingExistException extends RuntimeException {

    public NoKingExistException() {}

    public NoKingExistException(String message) {
        super(message);
    }
}
