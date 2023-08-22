package com.betelgeuse.chessai.Listeners;

import chess.backend.codes.abstractions.IMove;

public interface CheckMove {
    public  void isMovePlayedOnGame(IMove move);
}
