package com.koDong.view;

import com.koDong.domain.User.User;
import com.koDong.domain.chessBoard.ChessBoard;

public class ViewChessBoard {
    User whiteUser;
    User blackUser;

    public ViewChessBoard(User whiteUser, User blackUser) {
        this.whiteUser = whiteUser;
        this.blackUser = blackUser;
    }

    public void viewChessBoard(ChessBoard chessBoard) {
        // TODO (체스보드랑 다른 메시지랑 구분되도록, 위아래 한 칸 띄어주고, ----- 와 같은 기호로 추가하여 구현하기)

    }
}
