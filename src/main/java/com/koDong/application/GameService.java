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
import com.koDong.view.ViewChessBoard;

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
        ViewChessBoard view = new ViewChessBoard(whiteUser, blackUser);

        // main game loop
        boolean isRunning = true;
        while (isRunning) {
            view.viewChessBoard(chessBoard.getChessBoard());

            System.out.println(String.format("( %s turn )", turn.getColor()));
            Piece selectedPiece = selectPiece(moveV, turn); // 놓을 피스 선택하기 위한 위치

            view.viewChessBoard(chessBoard.getChessBoard());
            GameState gameState = placePiece(selectedPiece, turn, false ,moveV, checkV, checkmateV);

            System.out.println(gameState);
            System.out.println("");

            switch (gameState) {
                case GameState.CONTINUE -> {
                    // continue
                }
                case GameState.CHECK -> {
                    inCheckService(turn, setGameTurn, moveV, checkV, checkmateV, view);
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
    private void inCheckService(GameTurn turn, SetGameTurn st, MoveValidator moveV, CheckValidator checkV, CheckmateValidator checkmateV, ViewChessBoard view) {
        view.viewChessBoard(chessBoard.getChessBoard());

        kingCheckMessage(turn); // <WHITE또는 BLACK> king이 체크 입니다.

        st.updateGameTurn(turn);
        System.out.println(String.format("( %s turn )", turn.getColor()));

        boolean isRunning = true;
        while (isRunning) {
            view.viewChessBoard(chessBoard.getChessBoard());
            Piece selectedPiece = selectPiece(moveV, turn);
            GameState gameState = placePiece(selectedPiece, turn, true, moveV, checkV, checkmateV);
            view.viewChessBoard(chessBoard.getChessBoard());

            switch (gameState) {
                case GameState.CONTINUE -> {
                    isRunning = false;
                }
                case GameState.CHECK, GameState.CHECKMATE -> {
                    System.out.println("---- 해당 위치에는 놓을 수 없습니다. 다시 놓아주세요 ----");
                }
                default -> throw new IllegalStateException("도달할 수 없는 구문");
            }
        }
    }

    // WHITE또는 BLACK 킹이 체크되었다고 말해주기
    private void kingCheckMessage(GameTurn currentTurn) {
        switch (currentTurn) {
            case GameTurn.WHITE -> {
                System.out.println(String.format("---- %s king이 체크입니다. ----", "BLACK"));
            }
            case GameTurn.BLACK -> {
                System.out.println(String.format("---- %s king이 체크 입니다. ----", "WHITE"));
            }
        }

    }

    private Piece selectPiece(MoveValidator moveV, GameTurn turn) {
        // 체스보드에서 null을 선택했는지, 또 같은 컬러를 선택했는지 검증하는 로직
        int[] position; // 놓을 피스 선택하기 위한 위치
        int originX; // 놓을 피스의 x위치
        int originY; // 놓을 피스의 y위치
        Piece selectedPiece;
        while (true) {
            System.out.println("(-- 피스를 선택해주세요 --)");
            position = userInput.enterPosition();
            originX = position[0];
            originY = position[1];
            if (isSelectedPieceNull(originX, originY)) {
                // 체스보드에서 null을 선택한 경우
                System.out.println("---- 이 위치에는 아무런 피스가 없습니다. 다시 선택해주세요 ----");
            } else if (!(isColorSameWithCurrentTurn(originX, originY, turn))) {
                // 현재 턴 컬러와 같은 컬러의 피스를 선택하지 않은 경우
                System.out.println("---- 같은 컬러 피스를 선택해주세요 ----");
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
                    System.out.println(String.format("---- %s-%s-%d/%d는 현재 이동할 수 없습니다. 다시 선택해주세요 ----", selectedPiece.getColor(), selectedPiece.getType(), selectedPiece.getCurrentX(), selectedPiece.getCurrentY()));
                }
            }

        }

        return selectedPiece;
    }

    /**
     * 피스 놓기 (놓는 곳에 상대 피스가 있고, 놓을 수 있다면 공격도 가능)
     * @param selectedPiece 놓기 위한 피스
     * @param turn 현재 턴
     * @param inCheck 현재 체크 상태인지
     * @param moveV MoveValidator의 인스턴스
     * @param checkV CheckValidator의 인스턴스
     * @param checkmateV CheckmateValidator의 인스턴스
     * @return
     */
    private GameState placePiece(Piece selectedPiece, GameTurn turn, boolean inCheck, MoveValidator moveV, CheckValidator checkV, CheckmateValidator checkmateV) {
        int targetX;
        int targetY;
        while (true) {
            System.out.println("(-- 피스를 놓아주세요 --)");
            int[] targetPosition = userInput.enterPosition();
            targetX = targetPosition[0];
            targetY = targetPosition[1];
            if (selectedPiece.canMoveTo(targetX, targetY) && moveV.validateMove(chessBoard.getChessBoard(), selectedPiece, targetX, targetY)) {
                if (!inCheck) {
                    // 체크 상황이 아닐때 (일반상황)
                    chessBoard.placePiece(selectedPiece, targetX, targetY);

                    if (checkV.isCheck(turn, chessBoard)) {
                        if (checkmateV.isCheckmate(turn, chessBoard)) {
                            return GameState.CHECKMATE;
                        } else {
                            return GameState.CHECK;
                        }
                    }

                    // 피스를 놓았는데, 체크도 아니고 체크메이트도 아님
                    return GameState.CONTINUE;

                } else {
                    // 체크 상황일때 (inCheckService에서 사용되는 로직)
                    int originX = selectedPiece.getCurrentX(); // 놓을 피스의 놓기 전 x위치
                    int originY = selectedPiece.getCurrentY(); // 놓을 피스의 놓기 전 y위치

                    // 체크에서 풀려나면, 놓은 체스는 그대로(=실제로 놓기)
                    // 체크에서 풀려나지 않으면, 체스 원상복귀 (다시 놓기)
                    if (chessBoard.findPiece(targetX, targetY) == null) {
                        // 놓고자하는 위치에 아무런 피스가 없는 경우(null)에는
                        chessBoard.placePiece(selectedPiece, targetX, targetY);
                        if (!checkV.isCheck(turn, chessBoard)) {
                            // 체크에서 풀려남 (실제로 놓기)
                            return GameState.CONTINUE;
                        } else {
                            // 체크에서 풀려나지 않음 (여전히 check인 경우 또는 checkmate)
                            if (checkmateV.isCheckmate(turn, chessBoard)) {
                                return GameState.CHECKMATE;
                            }
                            chessBoard.restorePlace(originX, originY, targetX, targetY);
                            return GameState.CHECK;
                        }
                    } else {
                        // 놓고자하는 위치에 상대편 컬러의 피스가 있는 경우
                        Piece attackedPiece = chessBoard.placePieceForTest(selectedPiece, targetX, targetY);
                        if (!checkV.isCheck(turn, chessBoard)) {
                            // 체크에서 풀려남 (실제로 놓기)
                            return GameState.CONTINUE;
                        } else {
                            // 체크에서 풀려나지 않음 (여전히 check인 경우 또는 checkmate)
                            if (checkmateV.isCheckmate(turn, chessBoard)) {
                                return GameState.CHECKMATE;
                            }
                            chessBoard.restoreAttack(selectedPiece, attackedPiece, originX, originY, targetX, targetY);
                            return GameState.CHECK;
                        }
                    }
                }

            } else {
                System.out.println("---- 놓을 수 없는 위치입니다. ----");
                System.out.println("---- 위치를 다시 입력하세요 ----");
            }
        }

    }

    // 유저 초기화
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


    /**
     * selectPiece메서드에서 놓을 피스를 선택할 때, 입력받은 x, y에 있는 피스는 null인지 검사
     * @param originX 놓을 피스의 입력받은 x위치(null 확인 위해)
     * @param originY 놓을 피스의 입력받은 x위치(null 확인 위해)
     * @return 입력받은 x, y에 있는 피스가 null이라면 true반환
     */
    private boolean isSelectedPieceNull(int originX, int originY) {
        return chessBoard.findPiece(originX, originY) == null;
    }

    /**
     * 현재 턴 컬러와 같은 컬러의 피스를 선택했는지
     * @param originX 놓을 피스의 입력받은 x위치
     * @param originY 놓을 피스의 입력받은 Y위치
     * @param turn 현재 turn
     * @return 현재 턴 컬러와 같은 컬러의 피스를 선택했다면 true 반환
     */
    private boolean isColorSameWithCurrentTurn(int originX, int originY, GameTurn turn) {
        PieceColor currentTurnColor = switch (turn) {
            case GameTurn.WHITE -> {
                yield PieceColor.WHITE;
            }
            case GameTurn.BLACK -> {
                yield PieceColor.BLACK;
            }
            default -> throw new IllegalStateException("도달할 수 없는 구문");
        };

        return chessBoard.findPiece(originX, originY).getColor() == currentTurnColor;
    }

    // 게임의 전반적인 설명제공
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
