package com.betelgeuse.chessai.Controls;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.moveTypes.AMove;
import chess.backend.codes.concretes.moveTypes.EmptyMove;

public class MoveChipData{

    IPosition pos;
    public  String moveId="";
    public  String moveNumber="";
    public  String startTag="";
    public  String endTag="";
    public  String moveSymbol="";
    public MoveChipData(IMove move) {
        this.pos = move.getPositionAfterMoveExecuted();
        moveSymbol = move.getMoveSymbol();
        if (move instanceof EmptyMove) return;
        if (move.getPiece().getPieceColor()== PieceColor.WHITE) {
            moveId = String.valueOf(move.getId())+".";
        }
    }



}
