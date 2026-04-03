package com.koDong.utils;

import com.koDong.application.UserDecision;

import java.util.Scanner;

public class UserInput {

    Scanner sc;

    public UserInput(Scanner sc) {
        this.sc = sc;
    }

    public String enterUserName() {
        return sc.nextLine();
    }

    /**
     * x, y를 담고 있는 배열 반환 (x, y각각 0~7 값만 가질 수 있도록함)
     * @return return position
     */
    public int[] enterPosition() {
        int[] piecePositon = new int[2];
        int x;
        int y;
        String position;
        String[] positionSplit = new String[2];
        while (true) {
            try {
                position = sc.nextLine();
                position = position.replace(" ", ""); // 공백 제거
                positionSplit = position.split("/");
                if (!(positionSplit.length == 2)) {
                    // 좌표입력이 2개가 아닌경우 (ex. 2/2/4) --> 예외 발생
                    throw new IllegalStateException();
                }

                x = Integer.parseInt(positionSplit[0]);
                y = Integer.parseInt(positionSplit[1]);

                if ((x >= 1 && x <= 8) && (y >= 1 && y < 8)) {
                    break;
                } else {
                    System.out.println("---- 1~8 중의 값을 입력하세요 ----");
                }

            } catch (Exception e) {
                System.out.println("---- 다시 올바르게 입력해주세요 ----");
            }
        }
        piecePositon[0] = x-1; // x
        piecePositon[1] = y-1; // y

        return piecePositon;
    }

    public UserDecision yesOrNo() {

        String decision;
        while (true) {
            decision = sc.nextLine();
            decision = decision.replace(" ", ""); // 공백 제거

            if (decision.equals("y") || decision.equals("Y")) {
                return UserDecision.YES;
            }

            if (decision.equals("n") || decision.equals("N")) {
                return UserDecision.NO;
            }

            System.out.println("Y(y) 또는 N(n)로만 입력할 수 있습니다.");
        }

    }


}


