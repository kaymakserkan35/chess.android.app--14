package com.betelgeuse.chessai.board;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.PieceColor;

public interface IChessBoardControl extends IChessBoard {
    public  void setPlayerView(PieceColor color);
    public  void tooglePlayerView();


    public  void  randomlyPlay(IGame game);

}
