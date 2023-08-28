package com.betelgeuse.chessai.Listeners;

import chess.backend.codes.abstractions.IPosition;

public interface MoveItemClickListener {
    public void execute(IPosition position);
}
