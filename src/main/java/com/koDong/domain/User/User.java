package com.koDong.domain.User;

import com.koDong.domain.chessPiece.PieceColor;

public class User {
    String name;
    PieceColor color;

    public User(PieceColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
