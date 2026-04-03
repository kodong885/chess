package com.koDong.application;

import com.koDong.domain.GameTurn;
import com.koDong.domain.User.User;
import com.koDong.domain.chessBoard.ChessBoard;
import com.koDong.domain.chessPiece.Piece;
import com.koDong.domain.chessPiece.PieceColor;
import com.koDong.domain.validator.checkValidator.CheckValidator;
import com.koDong.domain.validator.checkmateValidator.CheckmateValidator;
import com.koDong.domain.validator.moveValidator.MoveValidator;
import com.koDong.utils.SetGameTurn;
import com.koDong.utils.UserInput;

public class GameService {
    ChessBoard chessBoard;
    UserInput userInput;

    public GameService(UserInput userInput, ChessBoard chessBoard) {
        this.userInput = userInput;
        this.chessBoard = chessBoard;
    }

    public void startGame() {
        User whiteUser = initializeUser(PieceColor.WHITE);
        User blackUser = initializeUser(PieceColor.BLACK);
        gameInformation();

        SetGameTurn setGameTurn = new SetGameTurn();
        GameTurn turn = setGameTurn.initializeGameTurn();

        MoveValidator moveV = new MoveValidator();
        CheckValidator checkV = new CheckValidator();
        CheckmateValidator checkmateV = new CheckmateValidator();

        // main game loop
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(String.format("<---- %s turn ---->", turn.getColor()));
            Piece selectedPiece = selectPiece(moveV, turn); // 놓을 피스 선택하기 위한 위치

            GameState gameState = placePiece(selectedPiece, turn, moveV, checkV, checkmateV, setGameTurn);

            System.out.println(gameState);
            switch (gameState) {
                case GameState.CONTINUE -> {
                    // continue
                }
                case GameState.CHECK -> {
                    inCheckService(turn, chessBoard, setGameTurn, userInput, moveV, checkV);
                }
                case GameState.CHECKMATE -> {
                    isRunning = false;
                }
                default -> throw new IllegalStateException("GameState는 CONTINUE, CHECK, CHECKMATE만 가능합니다.");
            }

            turn = setGameTurn.updateGameTurn(turn);
        }
        // Game End
    }

    /**
     * 체크 상황에서 작동하는 서비스 메서드
     */
    private void inCheckService(GameTurn turn, ChessBoard chessBoard, SetGameTurn st, UserInput userInput, MoveValidator moveV, CheckValidator checkV) {
        System.out.println("---- <컬러>의 킹이 체크 상황입니다. ----");

        External : while (true) {
            st.updateGameTurn(turn);

            System.out.println("---- 피스를 선택하세요 ----");
            int[] positon = userInput.enterPosition();
            Piece selectedPiece = chessBoard.findPiece(positon[0], positon[1]); // 옮겨지는 피스

            // 해당 피스를 놓았을때, 피스가 체크에서 벗어나게 할 수 있는 위치에만 놓을 수 있도록함.
            int targetX;
            int targetY;
            int originX = selectedPiece.getCurrentX(); // 놓을 피스의 놓기 전 x위치
            int originY = selectedPiece.getCurrentY(); // 놓을 피스의 놓기 전 y위치
            while (true) {
                System.out.println("---- 피스를 놓아주세요 ----");
                int[] targetPosition = userInput.enterPosition();
                targetX = targetPosition[0];
                targetY = targetPosition[1];
                if (selectedPiece.canMoveTo(targetX, targetY) && moveV.validateMove(chessBoard.getChessBoard(), selectedPiece, targetX, targetY)) {
                    // 체크에서 풀려나면, 놓은 체스는 그대로(=실제로 놓기)
                    // 체크에서 풀려나지 않으면, 체스 원상복귀
                    if (chessBoard.findPiece(targetX, targetY) == null) {
                        // 놓고자하는 위치에 아무런 피스가 없는 경우(null)에는
                        chessBoard.placePiece(selectedPiece, targetX, targetY);
                        if (!checkV.isCheck(turn, chessBoard)) {
                            // 체크에서 풀려남
                            break External;
                        } else {
                            // 체크에서 풀려나지않음 --> 다시 시도를 위해 원상복귀
                            chessBoard.restorePlace(originX, originY, targetX, targetY);
                        }
                    } else {
                        // 놓고자하는 위치에 상대편 컬러의 피스가 있는 경우
                        Piece attackedPiece = chessBoard.placePieceForTest(selectedPiece, targetX, targetY);
                        if (!checkV.isCheck(turn, chessBoard)) {
                            // 체크에서 풀려남
                            break External;
                        } else {
                            // 체크에서 풀려나지 않음
                            chessBoard.restoreAttack(selectedPiece, attackedPiece, originX, originY, targetX, targetY);
                        }
                    }

                }
            }

        }
    }

    private Piece selectPiece(MoveValidator moveV, GameTurn turn) {
        // 체스보드에서 null을 선택했는지 검증하는 로직
        int[] position; // 놓을 피스 선택하기 위한 위치
        int originX; // 놓을 피스의 x위치
        int originY; // 놓을 피스의 y위치
        Piece selectedPiece;
        while (true) {
            System.out.println("---- 피스를 선택해주세요 ----");
            position = userInput.enterPosition();
            originX = position[0];
            originY = position[1];
            if (chessBoard.findPiece(originX, originY) == null) {
                // 체스보드에서 null을 선택한 경우
                System.out.println("---- 이 위치에는 아무런 피스가 없습니다. 다시 선택해주세요 ----");
            } else {
                // 체스보드에서 null이 아닌 피스를 선택한 경우
                selectedPiece = chessBoard.findPiece(position[0], position[1]);
                if (moveV.canSelectedPieceMove(chessBoard, selectedPiece, turn)) {
                    // 선택된 피스가 갇혀있지 않은 경우 (어떤 곳이든 이동할 곳이 있음)
                    // 컬러-피스종류-x/y 순
                    System.out.println(String.format("(%s-%s-%d/%d 선택함)", selectedPiece.getColor(), selectedPiece.getType(), selectedPiece.getCurrentX(), selectedPiece.getCurrentY()));

                    System.out.println("---- 피스를 재선택하시습니까? (y/n) ----");
                    UserDecision decision = userInput.yesOrNo();
                    if (decision.equals(UserDecision.YES)) {
                        // 피스 재선택
                    } else {
                        // 피스 반환 (재선택X)
                        break;
                    }
                } else {
                    System.out.println(String.format("---- %s-%s-%d/%d는 현재 이동할 수 없습니다. 다시 선택해주세요 ----)", selectedPiece.getColor(), selectedPiece.getType(), selectedPiece.getCurrentX(), selectedPiece.getCurrentY()));
                }
            }

        }

        return selectedPiece;
    }

    /**
     * 피스 놓기
     * @param selectedPiece 놓기 위한 피스
     * @param turn
     * @param moveV
     * @param checkV
     * @param checkmateV
     * @param setGameTurn
     */
    private GameState placePiece(Piece selectedPiece, GameTurn turn, MoveValidator moveV, CheckValidator checkV, CheckmateValidator checkmateV, SetGameTurn setGameTurn) {
        int targetX;
        int targetY;
        while (true) {
            System.out.println("---- 피스를 놓아주세요 ----");
            int[] targetPosition = userInput.enterPosition();
            targetX = targetPosition[0];
            targetY = targetPosition[1];
            if (selectedPiece.canMoveTo(targetX, targetY) && moveV.validateMove(chessBoard.getChessBoard(), selectedPiece, targetX, targetY)) {
                chessBoard.placePiece(selectedPiece, targetX, targetY);

                if (checkV.isCheck(turn, chessBoard)) {
                    if (checkmateV.isCheckmate(turn, chessBoard)) {
                        return GameState.CHECKMATE;
                    } else {
                        return GameState.CHECK;
                    }
                }

                break; // while문을 종료 (CONTINUE)
            } else {
                System.out.println("---- 놓을 수 없는 위치입니다. ----");
                System.out.println("---- 위치를 다시 입력하세요 ----");
            }
        }
        return GameState.CONTINUE;
    }

    private User initializeUser(PieceColor color) {
        System.out.println("---- 이름을 입력해주세요 ----");

        if (color.equals(PieceColor.WHITE)) {
            // WHITE컬러 유저
            System.out.println("WHITE 유저 네임 :");
        } else {
            // BLACK컬러 유저
            System.out.println("BLACK 유저 네임 :");
        }
        String name = userInput.enterUserName();

        return new User(PieceColor.WHITE, name);
    }

    private void gameInformation() {
        System.out.println("""
        -------------------------------------------------
        ※ 피스의 위치를 입력하실 때는 숫자/숫자 형식으로 입력하셔야합니다.\s
        ※ 이때 숫자는 1부터 8까지의 숫자여야합니다.\s
        ※ ex) 1/8, 5/3, 6,6\s
        ※ 또 피스를 선택하셨지만, 취소하고 다른 피스를 입력하고 싶으시다면\s
        ※ /만 입력해주세요. \s
        ※ ex) 2/2를 입력했지만 다른피스를 선택하고 싶음 --> / (입력)\s
        -------------------------------------------------\s
       \s""");
    }

}
