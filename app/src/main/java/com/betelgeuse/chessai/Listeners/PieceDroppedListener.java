package com.betelgeuse.chessai.Listeners;

import com.betelgeuse.chessai.board.ICellControl;
import com.betelgeuse.chessai.chess.pieces.IPieceControl;

/** bu listenerı sadece tas bırakıldıgında cell siyah beyaz yapmak icin kullan.*/
public interface PieceDroppedListener {
    public void whenPieceDroppedOnSomeCell(ICellControl cell, IPieceControl pieceControl);
}
