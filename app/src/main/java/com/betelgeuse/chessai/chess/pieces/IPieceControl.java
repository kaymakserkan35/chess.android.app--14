package com.betelgeuse.chessai.chess.pieces;

import com.betelgeuse.chessai.Listeners.PieceDragStartedListener;

import chess.backend.codes.abstractions.Piece.*;

public interface IPieceControl extends IPiece {
    public void setAPieceDragStartedListener(PieceDragStartedListener pieceDragStartedListener);
}
