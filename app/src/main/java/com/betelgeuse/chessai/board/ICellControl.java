package com.betelgeuse.chessai.board;

import com.betelgeuse.chessai.Listeners.PieceDroppedListener;
import com.betelgeuse.chessai.chess.pieces.PieceControl;

import chess.backend.codes.abstractions.ICell;

public interface ICellControl extends ICell {

    public void removePiece();
    public PieceControl getPiece();
    public void setColor(int color);
    public  void  defaultColor();
    public void setPieceDroppedListener(PieceDroppedListener pieceDroppedListener);



}
