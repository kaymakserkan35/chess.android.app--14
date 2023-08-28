package com.betelgeuse.chessai.Listeners;

import com.betelgeuse.chessai.chess.pieces.IPieceControl;

public interface PieceDragStartedListener {
    public void whenPieceStartDraggingOnCells(IPieceControl piece);
}
