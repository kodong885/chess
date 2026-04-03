package com.koDong.domain;

public enum GameTurn {
    WHITE("WHITE"),
    BLACK("BLACK");

    private String color;

    private GameTurn(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }


}
