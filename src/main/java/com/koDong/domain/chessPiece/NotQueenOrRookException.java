package com.koDong.domain.chessPiece;

public class NotQueenOrRookException extends RuntimeException {

    public NotQueenOrRookException() {}

    public NotQueenOrRookException(String message) {
        super(message);
    }
}
